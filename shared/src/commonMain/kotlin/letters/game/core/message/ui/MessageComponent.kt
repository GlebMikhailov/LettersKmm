package letters.game.core.message.ui

import letters.game.core.bottomsheet.BottomSheetControl
import letters.game.core.message.domain.Message
import letters.game.core.message.ui.bottomsheet.MessageBottomSheetComponent
import ru.flawery.core.state.CNullableStateFlow

/**
 * A component for centralized message showing. There should be only one instance
 * of this component in the app connected to the root component.
 */
interface MessageComponent {

    val visibleMessage: CNullableStateFlow<Message>

    val bottomSheetControl: BottomSheetControl<*, MessageBottomSheetComponent>

    fun onActionClick()
}