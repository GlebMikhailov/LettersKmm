package letters.game.core.theme.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import letters.game.core.theme.custom.CustomColors
import letters.game.core.theme.custom.CustomTypography
import letters.game.core.theme.custom.LocalCustomColors
import letters.game.core.theme.custom.LocalCustomTypography

@Composable
fun CustomTheme(
    colors: CustomColors,
    typography: CustomTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCustomColors provides colors,
        LocalCustomTypography provides typography
    ) {
        content()
    }
}

object CustomTheme {
    val colors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomColors.current
            ?: throw Exception("CustomColors is not provided. Did you forget to apply CustomTheme?")

    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomTypography.current
            ?: throw Exception("CustomTypography is not provided. Did you forget to apply CustomTheme?")
}
