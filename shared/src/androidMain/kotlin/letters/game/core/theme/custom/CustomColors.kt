package letters.game.core.theme.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val isLight: Boolean,
    val background: BackgroundColors,
    val button: ButtonColors,
    val text: TextColors,
    val icon: IconColor
)

data class BackgroundColors(
    val screen: @Composable () -> Color,
    val invalid: @Composable () -> Color,
    val screenSunset: GradientColor,
    val letterBackground: @Composable () -> Color
)

data class GradientColor(
    val start: @Composable () -> Color,
    val end: @Composable () -> Color,
)

data class ButtonColors(
    val primary: PrimaryColor,
    val purple: GradientColor
)

data class PrimaryColor(
    val default: GradientColor,
    val pressed: GradientColor,
    val disabled: @Composable () -> Color,
    val link: @Composable () -> Color,
    val shadowColor: @Composable () -> Color,
    val error: GradientColor,
    val success: @Composable () -> Color
)

data class TextColors(
    val primary: PrimaryColor,
    val onError: @Composable () -> Color,
    val onSuccess: @Composable () -> Color,
    val backgroundInvert: @Composable () -> Color,
)

data class IconColor(
    val primary: @Composable () -> Color,
)


val LocalCustomColors = staticCompositionLocalOf<CustomColors?> { null }
