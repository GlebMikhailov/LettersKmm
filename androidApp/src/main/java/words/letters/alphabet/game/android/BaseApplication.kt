package words.letters.alphabet.game.android

import android.app.Application
import letters.game.core.Core
import letters.game.core.CoreProvider
import letters.game.core.configuration.Backend
import letters.game.core.configuration.BuildType
import letters.game.core.configuration.Configuration
import letters.game.core.configuration.Platform
import letters.game.core.debug_tools.AndroidDebugTools
import letters.game.core.launchAndroidDebugTools

abstract class BaseApplication : Application(), CoreProvider {

    abstract val versionCode: Int
    abstract val versionName: String
    abstract val debugTools: AndroidDebugTools

    final override lateinit var core: Core
        private set

    override fun onCreate() {
        super.onCreate()
        core = Core(getConfiguration())
        core.launchAndroidDebugTools()
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun getConfiguration() = Configuration(
        platform = Platform(
            application = this,
            activityClass = BaseActivity::class.java,
            debugTools = debugTools
        ),
        buildType = if (BuildConfig.DEBUG) BuildType.Debug else BuildType.Release,
        appVersionName = versionName,
        appVersionCode = versionCode,
        backend = if (BuildConfig.DEBUG) Backend.Development else Backend.Production
    )
}