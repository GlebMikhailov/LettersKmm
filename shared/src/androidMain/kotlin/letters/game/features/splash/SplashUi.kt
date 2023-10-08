package letters.game.features.splash

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
import letters.game.feautures.splash.ui.FakeSplashComponent
import letters.game.feautures.splash.ui.SplashComponent

@Composable
fun SplashUi(component: SplashComponent) {
    Box(Modifier.fillMaxSize().background(CustomTheme.colors.background.screenSunset.start())) {

    }
}

@Preview
@Composable
private fun HomeUiPreview() {
    AppTheme {
        SplashUi(component = FakeSplashComponent)
    }
}