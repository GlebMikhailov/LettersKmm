package letters.game.core.message.ui

import dev.icerock.moko.resources.desc.Raw
import dev.icerock.moko.resources.desc.StringDesc
import letters.game.core.bottomsheet.FakeBottomSheetControl
import letters.game.core.message.domain.Message
import letters.game.core.message.ui.bottomsheet.FakeMessageBottomSheetComponent
import letters.game.core.message.ui.bottomsheet.MessageBottomSheetComponent
import letters.game.core.state.CNullableMutableStateFlow

class FakeMessageComponent : MessageComponent {

    override val visibleMessage =
        CNullableMutableStateFlow(Message(StringDesc.Raw("Message")))

    override val bottomSheetControl =
        FakeBottomSheetControl<MessageBottomSheetComponent.Config, MessageBottomSheetComponent>(
            FakeMessageBottomSheetComponent()
        )

    override fun onActionClick() = Unit
}