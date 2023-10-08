package letters.game.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import letters.game.core.theme.custom.CustomTheme

@Composable
fun FullscreenCircularProgress(modifier: Modifier = Modifier, overlay: Boolean = false) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .run {
                if (overlay) {
                    background(CustomTheme.colors.background.screen().copy(alpha = 0.7f))
                        .blockClicks()
                } else {
                    this
                }
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = CustomTheme.colors.button.primary.default.start()
        )
    }
}

private fun Modifier.blockClicks() = pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            event.changes.forEach { it.consume() }
        }
    }
}