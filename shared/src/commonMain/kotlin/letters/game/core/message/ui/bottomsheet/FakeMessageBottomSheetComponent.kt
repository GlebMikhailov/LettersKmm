package letters.game.core.message.ui.bottomsheet

import letters.game.core.message.domain.HeavyMessage

class FakeMessageBottomSheetComponent : MessageBottomSheetComponent {

    override val message = HeavyMessage.MOCK

    override fun onActionClick() = Unit
}