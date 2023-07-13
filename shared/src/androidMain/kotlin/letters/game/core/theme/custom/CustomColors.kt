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
)

data class GradientColor(
    val start: @Composable () -> Color,
    val end: @Composable () -> Color,
)

data class ButtonColors(
    val primary: PrimaryColor,
)

data class PrimaryColor(
    val default: GradientColor,
    val pressed: GradientColor,
    val disabled: @Composable () -> Color,
    val link: @Composable () -> Color,
    val shadowColor: @Composable () -> Color
)

data class TextColors(
    val primary: PrimaryColor,
)

data class IconColor(
    val primary: @Composable () -> Color,
)


val LocalCustomColors = staticCompositionLocalOf<CustomColors?> { null }
