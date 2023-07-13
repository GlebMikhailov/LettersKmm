package letters.game.core.message.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import letters.game.core.ComponentFactory
import letters.game.core.bottomsheet.BottomSheetControl
import letters.game.core.bottomsheet.bottomSheetControl
import letters.game.core.message.createMessageBottomSheetComponent
import letters.game.core.message.data.MessageService
import letters.game.core.message.domain.HeavyMessage
import letters.game.core.message.domain.Message
import letters.game.core.message.ui.bottomsheet.MessageBottomSheetComponent
import ru.flawery.core.state.CNullableMutableStateFlow
import letters.game.core.utils.componentScope

class RealMessageComponent(
    componentContext: ComponentContext,
    private val messageService: MessageService,
    private val componentFactory: ComponentFactory
) : ComponentContext by componentContext, MessageComponent {

    companion object {
        private const val SHOW_TIME = 4000L
    }

    override var visibleMessage = CNullableMutableStateFlow<Message>(null)

    private var heavyMessageToShow: HeavyMessage? = null

    override val bottomSheetControl: BottomSheetControl<MessageBottomSheetComponent.Config, MessageBottomSheetComponent> =
        bottomSheetControl(
            key = "messageBottomSheetControl",
            bottomSheetComponentFactory = { config, componentContext, bottomSheetControl ->
                componentFactory.createMessageBottomSheetComponent(
                    componentContext,
                    heavyMessageToShow!!, // не крешнется, так как выставляем heavyMessageToShow непосредсвенно пред показом боттом-шита, и боттом-шит не persistent
                    bottomSheetControl
                ).also {
                    heavyMessageToShow = null
                }
            },
            persistent = false
        )

    private var autoDismissJob: Job? = null

    init {
        lifecycle.doOnCreate(::collectMessages)
    }

    override fun onActionClick() {
        autoDismissJob?.cancel()
        visibleMessage.value?.action?.invoke()
        visibleMessage.value = null
    }

    private fun collectMessages() {
        componentScope.launch {
            messageService.messageFlow.collect { message ->
                showMessage(message)
            }
        }

        componentScope.launch {
            messageService.heavyMessageFlow.collect { message ->
                showHeavyMessage(message)
            }
        }
    }

    private fun showMessage(message: Message) {
        autoDismissJob?.cancel()
        visibleMessage.value = message
        autoDismissJob = componentScope.launch {
            delay(SHOW_TIME)
            visibleMessage.value = null
        }
    }

    private fun showHeavyMessage(heavyMessage: HeavyMessage) {
        heavyMessageToShow = heavyMessage
        bottomSheetControl.show(MessageBottomSheetComponent.Config)
    }
}