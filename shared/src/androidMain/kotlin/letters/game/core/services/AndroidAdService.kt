package letters.game.core.services

import co.touchlab.kermit.Logger
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import letters.game.core.activity.ActivityProvider
import letters.game.core.configuration.Configuration
import letters.game.core.error_handling.ErrorHandler
import letters.game.core.error_handling.safeLaunch

class AndroidAdService(
    private val activityProvider: ActivityProvider,
    private val configuration: Configuration,
    private val errorHandler: ErrorHandler,
    private val appCoroutineScope: CoroutineScope
) : AdService {

    private var rewardedAd: RewardedAd =  RewardedAd(activityProvider.activity!!)
    private val adResult = MutableSharedFlow<AdResult>()

    init {
        rewardedAd.setAdUnitId(configuration.ad.yandex)
        rewardedAd.setRewardedAdEventListener(object : RewardedAdEventListener {
            override fun onAdLoaded() {
                rewardedAd.show()
                Logger.v("setRewardedAdEventListener: onAdLoaded")
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                Logger.v("setRewardedAdEventListener: onAdFailedToLoad $p0")
                appCoroutineScope.safeLaunch(errorHandler) {
                    adResult.emit(AdResult.Failed)
                }
            }

            override fun onAdShown() {
                Logger.v("setRewardedAdEventListener: onAdShown")
                appCoroutineScope.safeLaunch(errorHandler) {
                    adResult.emit(AdResult.Watched)
                }
            }

            override fun onAdDismissed() {
                Logger.v("setRewardedAdEventListener: onAdDismissed")
                appCoroutineScope.safeLaunch(errorHandler) {
                    adResult.emit(AdResult.Canceled)
                }
            }

            override fun onRewarded(p0: Reward) {
                Logger.v("setRewardedAdEventListener: : $p0")
            }

            override fun onAdClicked() {
                Logger.v("setRewardedAdEventListener: onAdClicked")
            }

            override fun onLeftApplication() {
                Logger.v("setRewardedAdEventListener: onLeftApplication")
            }

            override fun onReturnedToApplication() {
                Logger.v("setRewardedAdEventListener: onReturnedToApplication")
            }

            override fun onImpression(p0: ImpressionData?) {
                Logger.v("setRewardedAdEventListener: onImpression $p0")
            }
        })
    }

    override suspend fun showAd(): AdResult {
        Logger.v("activityProviderInAd: ${activityProvider.activity}")
        val adRequest: AdRequest = AdRequest.Builder().build()
        Logger.v("activityProviderInAd: [adRequest] $adRequest")
        rewardedAd.loadAd(adRequest)
        return adResult.first()
    }
}