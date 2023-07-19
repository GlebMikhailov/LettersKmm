package letters.game.core.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import letters.game.core.theme.AppTheme
import letters.game.core.theme.custom.CustomTheme

@Composable
fun AppButton(modifier: Modifier = Modifier, text: String) {
    val shape = RoundedCornerShape(16.dp)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val offset by animateDpAsState(
        targetValue = if (isPressed) 8.dp else 0.dp,
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { }
                )
                .offset(y = 8.dp)
                .background(color = CustomTheme.colors.button.primary.shadowColor(), shape = shape)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .offset(y = offset)
                .background(
                    color = CustomTheme.colors.button.primary.default.start(),
                    shape = shape
                )
        ) {
            Text(
                text = text,
                style = CustomTheme.typography.button.mediumNormal,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun AppButtonPreview() {
    AppTheme {
        AppButton(text = "Click me!")
    }
}