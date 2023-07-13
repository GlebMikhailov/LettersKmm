package letters.game.core.message

import com.arkivanov.decompose.ComponentContext
import letters.game.core.ComponentFactory
import org.koin.core.component.get
import letters.game.core.bottomsheet.BottomSheetControl
import letters.game.core.message.domain.HeavyMessage
import letters.game.core.message.ui.MessageComponent
import letters.game.core.message.ui.RealMessageComponent
import letters.game.core.message.ui.bottomsheet.MessageBottomSheetComponent
import letters.game.core.message.ui.bottomsheet.RealMessageBottomSheetComponent

fun ComponentFactory.createMessageComponent(
    componentContext: ComponentContext
): MessageComponent {
    return RealMessageComponent(componentContext, get(), get())
}

fun ComponentFactory.createMessageBottomSheetComponent(
    componentContext: ComponentContext,
    heavyMessage: HeavyMessage,
    bottomSheetControl: BottomSheetControl<*, *>
): MessageBottomSheetComponent {
    return RealMessageBottomSheetComponent(componentContext, heavyMessage, bottomSheetControl)
}