package letters.game.core.configuration

import android.app.Application
import letters.game.core.debug_tools.AndroidDebugTools

actual class Platform(
    val application: Application,
    val activityClass: Class<*>,
    val debugTools: AndroidDebugTools
) {
    actual val type: PlatformType = PlatformType.Android
}