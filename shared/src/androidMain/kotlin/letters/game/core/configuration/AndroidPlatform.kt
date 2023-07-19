package letters.game.core.configuration

import android.app.Application
import android.content.Context
import android.os.Build
import letters.game.core.debug_tools.AndroidDebugTools
import letters.game.feautures.splash.domain.Device

actual class Platform(
    val application: Application,
    val activityClass: Class<*>,
    val debugTools: AndroidDebugTools
) {
    actual val type: PlatformType = PlatformType.Android
}

fun Context.getStore(): Device.Store {
    val appContext = this.applicationContext
    val installerPackageName = try {
        val appPackageManager = appContext.packageManager
        val appPackageName = appContext.packageName

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            appPackageManager.getInstallSourceInfo(appPackageName).installingPackageName
        else
            appPackageManager.getInstallerPackageName(appPackageName)
    } catch (e: Exception) {
        e.printStackTrace()
        "--"
    }

    return when (installerPackageName) {
        "com.android.vending" -> Device.Store.GooglePlay
        "com.amazon.venezia" -> Device.Store.AppStore
        "com.huawei.appmarket" -> Device.Store.AppGallery
        else -> Device.Store.None
    }
}