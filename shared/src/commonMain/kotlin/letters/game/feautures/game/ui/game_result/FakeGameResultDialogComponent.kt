package letters.game.feautures.game.ui.game_result

class FakeGameResultDialogComponent : GameResultDialogComponent  {
    override fun onNegativeButtonClick() = Unit

    override fun onPositiveButtonClick(action: DialogAction) = Unit
}