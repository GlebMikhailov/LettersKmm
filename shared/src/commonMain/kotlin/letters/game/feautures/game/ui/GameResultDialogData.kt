package letters.game.feautures.game.ui

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import letters.game.MR

data class GameResultDialogData(
    val title: StringDesc,
    val positiveText: StringDesc,
    val negativeText: StringDesc,
) {
    companion object {
        fun mock(isLose: Boolean) = GameResultDialogData(
            title = if (isLose) MR.strings.game_dialog_lose_title.desc() else MR.strings.game_dialog_win_title.desc(),
            positiveText = if (isLose) MR.strings.game_dialog_lose_positive_btn_text.desc() else MR.strings.game_dialog_win_positive_btn_text.desc(),
            negativeText = if (isLose) MR.strings.game_dialog_lose_negative_btn_text.desc() else MR.strings.game_dialog_win_negative_btn_text.desc(),
        )
    }
}

data class GameResultDialogTypes(
    val lose: GameResultDialogData,
    val win: GameResultDialogData
) {
    companion object {
        val MOCK = GameResultDialogTypes(
            lose = GameResultDialogData.mock(true),
            win = GameResultDialogData.mock(false)
        )
    }
}

