package letters.game.core.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.StringDesc
import letters.game.MR
import letters.game.core.theme.AppTheme
import letters.game.core.theme.custom.CustomTheme
import letters.game.core.utils.clickableWithoutRipple

@Composable
fun BaseDialog(
    title: StringDesc,
    positiveButtonAction: () -> Unit,
    negativeButtonAction: () -> Unit,
    onDismissRequest: () -> Unit = {},
    positiveText: StringDesc,
    negativeText: StringDesc,
) {
    BaseDialog(
        title.localized(),
        positiveButtonAction,
        negativeButtonAction,
        onDismissRequest,
        positiveText.localized(),
        negativeText.localized(),
    )
}

@Composable
fun BaseDialog(
    title: String,
    positiveButtonAction: () -> Unit,
    negativeButtonAction: () -> Unit,
    onDismissRequest: () -> Unit,
    positiveText: String,
    negativeText: String,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(MR.files.animation_film.rawResId))
    val progress by animateLottieCompositionAsState(composition)
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = CustomTheme.colors.background.screen(),
            elevation = 3.dp,
            modifier = Modifier
                .wrapContentSize()
        ) {
            Column(
                Modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = CustomTheme.colors.text.primary.default.start(),
                    style = CustomTheme.typography.title.boldNormal,
                    fontSize = CustomTheme.typography.title.boldNormal.fontSize,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
                LottieAnimation(composition = composition, progress = { progress })
                AppButton(text = positiveText) {
                    positiveButtonAction()
                }
                Text(
                    modifier = Modifier.clickableWithoutRipple {
                        negativeButtonAction()
                    },
                    text = negativeText,
                    style = CustomTheme.typography.caption.boldSmall,
                    color = CustomTheme.colors.text.primary.disabled()
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun BaseDialogPreview() {
    AppTheme {
        BaseDialog(
            title = "You are lose",
            negativeButtonAction = { },
            positiveButtonAction = { },
            onDismissRequest = { },
            positiveText = "watch ad",
            negativeText = "Go to main menu"
        )
    }
}