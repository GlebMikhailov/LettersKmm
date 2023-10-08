package letters.game.feautures.game.ui

import kotlinx.coroutines.MainScope
import letters.game.core.dialog.DialogControl
import letters.game.core.dialog.FakeDialogControl
import letters.game.core.state.CMutableStateFlow
import letters.game.core.state.CStateFlow
import letters.game.core.utils.LoadableState
import letters.game.feautures.game.domain.Game
import letters.game.feautures.game.domain.GameUiState
import letters.game.feautures.game.domain.LetterAction
import letters.game.feautures.game.domain.LetterState
import letters.game.feautures.game.domain.getLetterState
import letters.game.feautures.game.ui.game_result.FakeGameResultDialogComponent
import letters.game.feautures.game.ui.game_result.GameResultDialogComponent
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.options.KeyboardOptions

class FakeGameComponent : GameComponent {

    override val gameResultDialogsInfo: GameResultDialogTypes = GameResultDialogTypes.MOCK

    override val game: CStateFlow<LoadableState<Game>> =
        CMutableStateFlow(LoadableState(false, data = Game.MOCK))
    override val wordInputControl: InputControl = InputControl(
        coroutineScope = MainScope(),
        maxLength = 4,
        keyboardOptions = KeyboardOptions()
    )
    override val lettersState: CMutableStateFlow<List<List<LetterState>>> =
        CMutableStateFlow(game.value.data!!.attempts.map {
            it.word.name.getLetterState(game.value.data!!.word.name)
        })
    override val currentEditingRow: CMutableStateFlow<Int> = CMutableStateFlow(2)
    override val currentEditingCell: CMutableStateFlow<Int> = CMutableStateFlow(0)

    override val gameUiState: CMutableStateFlow<GameUiState> =
        CMutableStateFlow(GameUiState.Playing)
    override val gameResultDialogControl: DialogControl<GameResultDialogComponent.DialogConfig, GameResultDialogComponent> = FakeDialogControl(FakeGameResultDialogComponent())

    override fun onRefresh() = Unit

    override fun onRetryClick() = Unit
    override fun onDialogDismiss() = Unit
    override fun onLetterClick(action: LetterAction) = Unit
}