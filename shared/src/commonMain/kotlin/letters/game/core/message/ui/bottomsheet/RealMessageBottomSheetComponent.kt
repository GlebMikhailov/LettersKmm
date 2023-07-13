package letters.game.core.message.ui.bottomsheet

import com.arkivanov.decompose.ComponentContext
import letters.game.core.bottomsheet.BottomSheetControl
import letters.game.core.message.domain.HeavyMessage

class RealMessageBottomSheetComponent(
    componentContext: ComponentContext,
    override val message: HeavyMessage,
    private val bottomSheetControl: BottomSheetControl<*, *>
) : ComponentContext by componentContext, MessageBottomSheetComponent {

    override fun onActionClick() {
        message.action?.invoke()
        bottomSheetControl.dismiss()
    }
}