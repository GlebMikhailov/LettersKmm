package letters.game.feautures.root.ui

import com.arkivanov.decompose.router.stack.ChildStack
import letters.game.core.message.ui.MessageComponent
import letters.game.core.state.CStateFlow
import letters.game.feautures.game.ui.GameComponent
import letters.game.feautures.home.ui.HomeComponent
import letters.game.feautures.splash.ui.SplashComponent

/**
 * A root of a Decompose component tree.
 *
 * Note: Try to minimize child count in RootComponent. It should operate by flows of screens rather than separate screens.
 */
interface RootComponent {


    val childStack: CStateFlow<ChildStack<*, Child>>

    val messageComponent: MessageComponent


    sealed interface Child {
        class Splash(val component: SplashComponent) : Child
        class Home(val component: HomeComponent) : Child
        class Game(val component: GameComponent) : Child
    }
}