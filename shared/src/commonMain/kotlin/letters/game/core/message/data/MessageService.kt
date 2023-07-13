package letters.game.core.message.data

import kotlinx.coroutines.flow.Flow
import letters.game.core.message.domain.HeavyMessage
import letters.game.core.message.domain.Message

/**
 * A service for centralized message showing
 */
interface MessageService {

    val messageFlow: Flow<Message>

    val heavyMessageFlow: Flow<HeavyMessage>

    fun showMessage(message: Message)

    fun showMessage(message: HeavyMessage)
}