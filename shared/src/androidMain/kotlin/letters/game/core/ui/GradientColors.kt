package letters.game.core.ui

import androidx.compose.ui.graphics.Color

data class GradientColors(
    val start: Color,
    val end: Color
) {
    companion object {
        fun notGradient(color: Color) = GradientColors(
            start = color,
            end = color
        )
    }
}