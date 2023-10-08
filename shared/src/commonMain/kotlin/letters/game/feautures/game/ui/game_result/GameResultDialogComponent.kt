package letters.game.feautures.game.ui.game_result

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface GameResultDialogComponent {
    fun onNegativeButtonClick()

    fun onPositiveButtonClick(action: DialogAction)

    @Parcelize
    object DialogConfig : Parcelable
}

enum class DialogAction {
    GetMoreAttempts, GoToMainMenu, Restart
}