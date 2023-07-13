package letters.game.core.bottomsheet

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.dp
import letters.game.core.theme.custom.CustomTheme

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun <T : Any> ModalBottomSheet(
    modifier: Modifier = Modifier,
    control: BottomSheetControl<*, T>,
    bottomSheetContent: @Composable (T) -> Unit
) {
    val bottomSheetOverlay by control.sheetOverlay.collectAsState()
    val componentBottomSheetState by control.sheetState.collectAsState()

    val currentComponent = bottomSheetOverlay.overlay?.instance
    // эта ссылка на компонент, которая зануляется с задержкой - после того как закончится анимация скрытия боттом-шита
    var delayedComponent by remember(control) { mutableStateOf(currentComponent) }

    LaunchedEffect(currentComponent) {
        if (currentComponent != null) {
            delayedComponent = currentComponent
        }
    }

    val initialComposeState = remember(control) { componentBottomSheetState.toCompose() }
    val skipHalfExpended = !control.halfExpandingSupported
    val confirmValueChange = remember<(ModalBottomSheetValue) -> Boolean>(control) {
        {
            val newState = it.toSheetControlState()
            val canChange = control.shouldUpdateState(newState)
            if (canChange) {
                control.onStateChangedFromUI(newState)
            }
            canChange
        }
    }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = initialComposeState,
        skipHalfExpanded = skipHalfExpended,
        confirmValueChange = { confirmValueChange(it) }
    )

    LaunchedEffect(componentBottomSheetState) {
        if (componentBottomSheetState == BottomSheetControl.State.Hidden) {
            modalBottomSheetState.hide()
            delayedComponent = null
        } else {
            modalBottomSheetState.show()
        }
    }

    ModalBottomSheetUi(
        modalBottomSheetState = modalBottomSheetState,
        component = currentComponent ?: delayedComponent,
        bottomSheetContent = bottomSheetContent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
private fun BottomSheetControl.State.toCompose(): ModalBottomSheetValue =
    when (this) {
        BottomSheetControl.State.Expanded -> ModalBottomSheetValue.Expanded
        BottomSheetControl.State.Hidden -> ModalBottomSheetValue.Hidden
        BottomSheetControl.State.HalfExpanded -> ModalBottomSheetValue.HalfExpanded
    }

@OptIn(ExperimentalMaterialApi::class)
private fun ModalBottomSheetValue.toSheetControlState(): BottomSheetControl.State =
    when (this) {
        ModalBottomSheetValue.Expanded -> BottomSheetControl.State.Expanded
        ModalBottomSheetValue.Hidden -> BottomSheetControl.State.Hidden
        ModalBottomSheetValue.HalfExpanded -> BottomSheetControl.State.HalfExpanded
    }

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun <T : Any> ModalBottomSheetUi(
    modalBottomSheetState: ModalBottomSheetState,
    component: T?,
    bottomSheetContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ScrimUi(
            color = ModalBottomSheetDefaults.scrimColor,
            visible = modalBottomSheetState.targetValue != ModalBottomSheetValue.Hidden
        )
    }

    ModalBottomSheetLayout(
        scrimColor = Color.Transparent,
        modifier = modifier.statusBarsPadding(),
        sheetContent = {
            if (component != null) {
                Box(modifier = Modifier.navigationBarsPadding()) {
                    bottomSheetContent(component)
                }
            }
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetBackgroundColor = CustomTheme.colors.background.screen()
    ) {}
}

@Composable
private fun ScrimUi(
    color: Color,
    visible: Boolean
) {
    if (color.isSpecified) {
        val alpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = TweenSpec()
        )

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawRect(color = color, alpha = alpha)
        }
    }
}