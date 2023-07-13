package letters.game.core.message.data

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import letters.game.core.message.domain.HeavyMessage
import letters.game.core.message.domain.Message

class MessageServiceImpl : MessageService {

    private val messageChannel = Channel<Message>(Channel.UNLIMITED)
    override val messageFlow = messageChannel.receiveAsFlow()

    private val heavyMessageChannel = Channel<HeavyMessage>(Channel.UNLIMITED)
    override val heavyMessageFlow = heavyMessageChannel.receiveAsFlow()

    override fun showMessage(message: Message) {
        messageChannel.trySend(message)
    }

    override fun showMessage(message: HeavyMessage) {
        heavyMessageChannel.trySend(message)
    }
}