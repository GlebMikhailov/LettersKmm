package letters.game.features.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import letters.game.MR
import letters.game.core.theme.AppTheme
import letters.game.core.theme.custom.CustomTheme
import letters.game.core.utils.systemBarsWithImePadding
import letters.game.core.widgets.AppButton
import letters.game.features.game.Field
import letters.game.feautures.game.domain.getLetterState
import letters.game.feautures.home.ui.FakeHomeComponent
import letters.game.feautures.home.ui.HomeComponent

@Composable
fun HomeUi(component: HomeComponent) {
    val commonField = component.commonFieldData
    val animatedTitleColor = remember { Animatable(0f) }
    val maxTextPadding = 72.dp.value - 40f.dp.value
    val maxHeight = CustomTheme.typography.title.boldNormal.fontSize.value * 1.86f + maxTextPadding

    LaunchedEffect(animatedTitleColor) {
        animatedTitleColor.animateTo(
            targetValue = 1f, animationSpec = repeatable(
                iterations = 1,
                animation = tween(2500, easing = LinearOutSlowInEasing)
            )
        )
    }

    Box(
        Modifier
            .fillMaxSize()
            .systemBarsWithImePadding()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CustomTheme.colors.background.screenSunset.start(),
                        CustomTheme.colors.background.screenSunset.end()
                    )
                )
            )
    ) {
        Column(Modifier.align(Alignment.Center)) {
            Field(
                lettersState = commonField.map { it.localized().getLetterState(commonField.last().localized()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                inputControl = component.wordInputControl,
                onUpdatingLetterState = {  }
            )
        }
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    CustomTheme.colors.background
                        .letterBackground()
                        .copy(alpha = .1f)
                )
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(maxTextPadding.dp))
            Text(
                text = StringDesc.ResourceFormatted(
                    MR.strings.home_welcome,
                    MR.strings.common_app_name.desc().localized()
                ).localized(),
                color = CustomTheme.colors.text.backgroundInvert(),
                style = CustomTheme.typography.title.boldNormal,
                fontSize = CustomTheme.typography.title.boldNormal.fontSize * (animatedTitleColor.value + 0.86f),
                modifier = Modifier
                    .height(maxHeight.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = MR.strings.home_play.desc().localized()
            )
            AppButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = MR.strings.home_set_language.desc().localized()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
private fun HomeUiPreview() {
    AppTheme {
        HomeUi(component = FakeHomeComponent())
    }
}