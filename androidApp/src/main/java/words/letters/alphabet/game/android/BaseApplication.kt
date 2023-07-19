package words.letters.alphabet.game.android

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.provider.Settings
import letters.game.core.Core
import letters.game.core.CoreProvider
import letters.game.core.configuration.Backend
import letters.game.core.configuration.BuildType
import letters.game.core.configuration.Configuration
import letters.game.core.configuration.DeviceInfo
import letters.game.core.configuration.Platform
import letters.game.core.configuration.getStore
import letters.game.core.debug_tools.AndroidDebugTools
import letters.game.core.launchAndroidDebugTools
import java.util.Locale


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

    @SuppressLint("HardwareIds")
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
        backend = if (BuildConfig.DEBUG) Backend.Development else Backend.Production,
        device = DeviceInfo(
            deviceId = Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ANDROID_ID
            ),
            store = applicationContext.getStore(),
            model = getDeviceName()
        )
    )

    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.lowercase(Locale.getDefault())
                .startsWith(manufacturer.lowercase(Locale.getDefault()))
        ) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }


    private fun capitalize(s: String?): String {
        if (s.isNullOrEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            first.uppercaseChar().toString() + s.substring(1)
        }
    }
}