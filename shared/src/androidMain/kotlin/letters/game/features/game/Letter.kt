package letters.game.features.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import letters.game.core.theme.AppTheme
import letters.game.core.theme.custom.CustomTheme
import letters.game.core.ui.GradientColors
import letters.game.feautures.game.domain.LetterState

@Composable
fun Letter(
    modifier: Modifier = Modifier,
    index: Int,
    text: String,
    letterState: LetterState,
    hasFocus: Boolean
) {
    val isFocused = hasFocus && text.length == index
    val color = when (letterState) {
        is LetterState.Input -> CustomTheme.colors.text.backgroundInvert()

        is LetterState.InvalidPosition -> CustomTheme.colors.button.purple.start()

        is LetterState.Success -> CustomTheme.colors.text.primary.success()

        is LetterState.Error -> CustomTheme.colors.button.primary.error.start()

        LetterState.Empty -> Color.Transparent
    }
    val boarderColor = when {
        isFocused -> CustomTheme.colors.button.primary.default.start()
        else -> Color.Transparent
    }

    Box(
        Modifier
            .size(64.dp)
            .background(
                CustomTheme.colors.background.letterBackground(),
                shape = RoundedCornerShape(16.dp)
            )
            .border(3.dp, boarderColor, RoundedCornerShape(16.dp)).then(modifier)
    ) {
        Text(
            text = letterState.symbol.uppercase(),
            color = color,
            style = CustomTheme.typography.title.boldVeryLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LetterPreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.background(
                Brush.verticalGradient(
                    colors = listOf(
                        CustomTheme.colors.background.screenSunset.start(),
                        CustomTheme.colors.background.screenSunset.end()
                    )
                )
            )
        ) {
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Letter(
//                    letterState = LetterState.Error("E")
//                )
//                Letter(
//                    letterState = LetterState.Success("S")
//                )
//            }
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                Letter(
//                    letterState = LetterState.Input("I")
//                )
//                Letter(
//                    letterState = LetterState.InvalidPosition("P")
//                )
//            }
        }
    }
}

