package letters.game.feautures.root.ui

import com.arkivanov.decompose.router.stack.ChildStack
import letters.game.feautures.home.ui.HomeComponent
import letters.game.core.message.ui.MessageComponent
import ru.flawery.core.state.CStateFlow

/**
 * A root of a Decompose component tree.
 *
 * Note: Try to minimize child count in RootComponent. It should operate by flows of screens rather than separate screens.
 */
interface RootComponent {


    val childStack: CStateFlow<ChildStack<*, Child>>

    val messageComponent: MessageComponent

    sealed interface Child {
        class Home(val component: HomeComponent) : Child
    }
}