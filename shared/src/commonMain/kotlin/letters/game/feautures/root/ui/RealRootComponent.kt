package letters.game.feautures.root.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import letters.game.core.ComponentFactory
import letters.game.core.error_handling.ErrorHandler
import letters.game.feautures.home.createHomeComponent
import letters.game.feautures.home.ui.HomeComponent
import letters.game.core.message.createMessageComponent
import letters.game.core.state.CStateFlow
import letters.game.core.utils.safePush
import letters.game.core.utils.setupLogging
import letters.game.core.utils.toCStateFlow
import letters.game.feautures.game.domain.GameId

class RealRootComponent(
    componentContext: ComponentContext,
    private val componentFactory: ComponentFactory,
    private val errorHandler: ErrorHandler,
    private val onCloseApp: () -> Unit
) : ComponentContext by componentContext, RootComponent {

    init {
        backHandler.register(BackCallback { onCloseApp() })
    }

    private val navigation = StackNavigation<ChildConfig>()

    override val childStack: CStateFlow<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = ChildConfig.Home,
        handleBackButton = true,
        childFactory = ::createChild
    ).setupLogging("Root").toCStateFlow(lifecycle)


    override val messageComponent = componentFactory.createMessageComponent(
        childContext(key = "message")
    )

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        ChildConfig.Home -> {
            RootComponent.Child.Home(componentFactory.createHomeComponent(componentContext, ::onHomeOutput))
        }

        is ChildConfig.Game -> TODO()
    }

    private fun onHomeOutput(output: HomeComponent.Output) {
        when (output) {
            is HomeComponent.Output.StartGame -> {
                navigation.safePush(ChildConfig.Game(output.gameId))
            }
        }
    }

    private sealed interface ChildConfig : Parcelable {
        @Parcelize
        object Home : ChildConfig

        @Parcelize
        data class Game(val id: GameId) : ChildConfig
    }
}