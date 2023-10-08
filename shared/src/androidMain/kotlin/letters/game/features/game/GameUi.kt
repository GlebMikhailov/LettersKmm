package letters.game.features.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import letters.game.core.theme.AppTheme
import letters.game.core.theme.custom.CustomTheme
import letters.game.core.widgets.BaseDialog
import letters.game.core.widgets.SwipeRefreshLceWidget
import letters.game.feautures.game.domain.GameUiState
import letters.game.feautures.game.domain.Letter
import letters.game.feautures.game.ui.FakeGameComponent
import letters.game.feautures.game.ui.GameComponent
import letters.game.feautures.game.ui.game_result.DialogAction
import letters.game.feautures.game.ui.game_result.GameResultDialogComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameUi(component: GameComponent) {
    val gameState by component.game.collectAsState()
    val lettersState by component.lettersState.collectAsState()
    val currentEditingRow by component.currentEditingRow.collectAsState()
    val currentEditingCell by component.currentEditingCell.collectAsState()
    val letters = Letter.russianLetters()
    val gameResultState by component.gameUiState.collectAsState()
    val dialogOverlay by component.gameResultDialogControl.dialogOverlay.collectAsState()
    val dialogComponent = dialogOverlay.overlay?.instance
    Box(
        Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background.screenSunset.start())
            .systemBarsPadding()
    ) {
        SwipeRefreshLceWidget(
            contentModifier = Modifier.fillMaxSize(),
            state = gameState,
            onRefresh = component::onRefresh,
            onRetryClick = component::onRetryClick
        ) { game, _ ->
            Column {
                Field(
                    lettersState = lettersState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    inputControl = component.wordInputControl,
                    field = game.field,
                    currentEditingRow = currentEditingRow,
                    currentEditingCell = currentEditingCell
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomKeyboard(
                    keyboardLetters = letters,
                    onLetterClick = component::onLetterClick
                )
            }
        }
    }
    if (dialogComponent is GameResultDialogComponent) {
        when (gameResultState) {
            is GameUiState.Lose -> {
                BaseDialog(
                    title = component.gameResultDialogsInfo.lose.title,
                    positiveText = component.gameResultDialogsInfo.lose.positiveText,
                    negativeText = component.gameResultDialogsInfo.lose.negativeText,
                    positiveButtonAction = { dialogComponent.onPositiveButtonClick(DialogAction.GetMoreAttempts) },
                    negativeButtonAction = { dialogComponent.onPositiveButtonClick(DialogAction.GoToMainMenu) },
                )
            }

            GameUiState.Playing -> Unit
            is GameUiState.Win -> {
                BaseDialog(
                    title = component.gameResultDialogsInfo.win.title,
                    positiveText = component.gameResultDialogsInfo.win.positiveText,
                    negativeText = component.gameResultDialogsInfo.win.negativeText,
                    positiveButtonAction = { dialogComponent.onPositiveButtonClick(DialogAction.Restart) },
                    negativeButtonAction = { dialogComponent.onPositiveButtonClick(DialogAction.GoToMainMenu) },
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FieldRowUiPreview() {
    AppTheme {
        GameUi(FakeGameComponent())
    }
}
