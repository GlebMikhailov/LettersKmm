package letters.game.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import dev.icerock.moko.resources.compose.localized
import letters.game.core.bottomsheet.ModalBottomSheet
import letters.game.core.message.ui.FakeMessageComponent
import letters.game.core.theme.AppTheme
import letters.game.core.message.domain.Message
import letters.game.core.message.ui.MessageComponent
import letters.game.core.theme.custom.CustomTheme

/**
 * Displays a [Message] as a popup at the bottom of screen.
 */
@Composable
fun MessageUi(
    component: MessageComponent,
    modifier: Modifier = Modifier,
    bottomPadding: Dp
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val navigationBarHeight = systemBarsPadding.calculateBottomPadding()

    val additionalBottomPadding = with(LocalDensity.current) {
        LocalMessageOffsets.current.values.maxOrNull()?.toDp() ?: 0.dp
    } + navigationBarHeight

    val visibleMessage by component.visibleMessage.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        visibleMessage?.let {
            MessagePopup(
                message = it,
                bottomPadding = bottomPadding + additionalBottomPadding,
                onAction = component::onActionClick
            )
        }
    }
}

@Composable
private fun MessagePopup(
    message: Message,
    onAction: () -> Unit,
    bottomPadding: Dp
) {
    Popup(
        alignment = Alignment.BottomCenter,
        properties = PopupProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = CustomTheme.colors.background.screen(),
            elevation = 3.dp,
            modifier = Modifier
                .padding(bottom = bottomPadding, start = 8.dp, end = 8.dp)
                .wrapContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(vertical = 13.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = message.text.localized(),
                    color = CustomTheme.colors.text.primary.default.start(),
                    style = CustomTheme.typography.body.regularNormal
                )
                message.actionTitle?.let {
                    MessageButton(text = it.localized(), onClick = onAction)
                }
            }
        }
    }
}

@Composable
private fun MessageButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = CustomTheme.typography.button.mediumNormal
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MessageUiPreview() {
    AppTheme {
        MessageUi(FakeMessageComponent(), Modifier, 40.dp)
    }
}