package letters.game.core.message.ui.bottomsheet

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import letters.game.core.message.domain.HeavyMessage

interface MessageBottomSheetComponent {

    val message: HeavyMessage

    fun onActionClick()

    @Parcelize
    object Config : Parcelable
}