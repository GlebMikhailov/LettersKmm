package letters.game.feautures.game.ui

import letters.game.core.dialog.DialogControl
import letters.game.core.state.CMutableStateFlow
import letters.game.core.state.CStateFlow
import letters.game.core.utils.LoadableState
import letters.game.feautures.game.domain.Game
import letters.game.feautures.game.domain.GameUiState
import letters.game.feautures.game.domain.LetterAction
import letters.game.feautures.game.domain.LetterState
import letters.game.feautures.game.ui.game_result.GameResultDialogComponent
import ru.mobileup.kmm_form_validation.control.InputControl

interface GameComponent {

    val gameResultDialogsInfo: GameResultDialogTypes

    val game: CStateFlow<LoadableState<Game>>

    val wordInputControl: InputControl

    val lettersState: CMutableStateFlow<List<List<LetterState>>>

    val currentEditingRow: CMutableStateFlow<Int>

    val currentEditingCell: CMutableStateFlow<Int>

    val gameUiState: CMutableStateFlow<GameUiState>

    val gameResultDialogControl: DialogControl<GameResultDialogComponent.DialogConfig, GameResultDialogComponent>

    fun onRefresh()
    fun onRetryClick()
    fun onDialogDismiss()
    fun onLetterClick(action: LetterAction)

    sealed interface Output {
        object Home : Output
    }
}