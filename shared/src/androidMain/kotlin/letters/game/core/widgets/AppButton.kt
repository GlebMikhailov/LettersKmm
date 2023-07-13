package letters.game.core.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import letters.game.core.theme.custom.CustomTheme

private const val buttonAnimationKey = "buttonAnimationKey"

@Composable
fun AppButton() {
    val shape = RoundedCornerShape(16.dp)
    var originHeight by remember { mutableStateOf(56.dp) }
    val transition = updateTransition(targetState = originHeight, label = buttonAnimationKey)
    val height by transition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = 400
                originHeight at 0 with FastOutSlowInEasing
                originHeight at 48 with FastOutSlowInEasing
            }
        }, label = buttonAnimationKey
    ) { it }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    originHeight = if (originHeight == 56.dp) 48.dp else 56.dp
                }
            )
            .animateContentSize()
            .background(color = CustomTheme.colors.button.primary.shadowColor(), shape = shape)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = CustomTheme.colors.button.primary.default.start(), shape = shape)) {

        }
    }
}