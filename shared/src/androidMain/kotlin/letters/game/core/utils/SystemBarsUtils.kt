package letters.game.core.utils

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalSystemBarsSettings =
    staticCompositionLocalOf { mutableStateMapOf<Int, SystemBarsSettings>() }

data class SystemBarsSettings(
    val transparentNavigationBar: Boolean
)

@Composable
fun SystemBars(transparentNavigationBar: Boolean) {
    val key = currentCompositeKeyHash
    val systemBarsSettings = SystemBarsSettings(transparentNavigationBar)
    val systemBarsSettingsMap = LocalSystemBarsSettings.current

    DisposableEffect(systemBarsSettings) {
        systemBarsSettingsMap[key] = systemBarsSettings
        onDispose { systemBarsSettingsMap.remove(key) }
    }
}

@Composable
fun Map<Int, SystemBarsSettings>.accumulate(): SystemBarsSettings {
    return SystemBarsSettings(
        transparentNavigationBar = values.any { it.transparentNavigationBar }
    )
}

fun Modifier.systemBarsWithImePadding() = systemBarsPadding().imePadding()