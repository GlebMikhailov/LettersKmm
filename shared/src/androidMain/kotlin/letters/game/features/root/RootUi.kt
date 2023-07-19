package letters.game.features.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import letters.game.core.permission.BindPermissionController
import letters.game.core.theme.AppTheme
import letters.game.feautures.root.ui.FakeRootComponent
import letters.game.feautures.root.ui.RootComponent
import letters.game.core.ui.MessageUi
import letters.game.core.utils.LocalSystemBarsSettings
import letters.game.core.utils.accumulate
import letters.game.core.theme.custom.CustomTheme
import letters.game.features.home.HomeUi

@Composable
fun RootUi(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.collectAsState()

    BindPermissionController()
    SystemBarColors()

    Children(
        stack = childStack
    ) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.Home -> HomeUi(instance.component)
            is RootComponent.Child.Game -> TODO()
        }
    }


    MessageUi(
        modifier = modifier,
        component = component.messageComponent,
        bottomPadding = 16.dp
    )
}

@Composable
private fun SystemBarColors() {
    val edgeToEdgeSettings = LocalSystemBarsSettings.current.accumulate()

    val navigationBarColor =
        if (edgeToEdgeSettings.transparentNavigationBar) Color.Transparent else CustomTheme.colors.background.screen()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent)
    systemUiController.setNavigationBarColor(navigationBarColor, darkIcons = true)
    systemUiController.statusBarDarkContentEnabled = true
}

@Preview(showSystemUi = true)
@Composable
fun RootUiPreview() {
    AppTheme {
        RootUi(FakeRootComponent())
    }
}