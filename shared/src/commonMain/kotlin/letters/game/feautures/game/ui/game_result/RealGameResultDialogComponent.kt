package letters.game.feautures.game.ui.game_result

import letters.game.core.dialog.DialogControl
import letters.game.feautures.game.domain.GameUiState

class RealGameResultDialogComponent(
    private val initialState: GameUiState,
    private val dialogControl: DialogControl<GameResultDialogComponent.DialogConfig, GameResultDialogComponent>,
    val onAction: (DialogAction) -> Unit,
) : GameResultDialogComponent {
    override fun onNegativeButtonClick() {
        dialogControl.dismiss()
    }

    override fun onPositiveButtonClick(action: DialogAction) {
        this.onAction(action)
        dialogControl.dismiss()
    }
}