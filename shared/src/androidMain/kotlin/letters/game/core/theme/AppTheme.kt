package letters.game.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import letters.game.core.theme.custom.CustomTheme
import letters.game.core.theme.custom.toMaterialColors
import letters.game.core.theme.custom.toMaterialTypography

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) DarkAppColors else LightAppColors
    val typography = AppTypography

    CustomTheme(colors, typography) {
        MaterialTheme(
            colors = colors.toMaterialColors(),
            typography = typography.toMaterialTypography(),
            content = content
        )
    }
}