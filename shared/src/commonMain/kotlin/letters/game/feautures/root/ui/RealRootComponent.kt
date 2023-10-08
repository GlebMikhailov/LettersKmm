package letters.game.feautures.root.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import letters.game.core.ComponentFactory
import letters.game.core.error_handling.ErrorHandler
import letters.game.core.message.createMessageComponent
import letters.game.core.state.CStateFlow
import letters.game.core.utils.safePush
import letters.game.core.utils.setupLogging
import letters.game.core.utils.toCStateFlow
import letters.game.feautures.game.createGameComponent
import letters.game.feautures.game.domain.GameId
import letters.game.feautures.game.ui.GameComponent
import letters.game.feautures.home.createHomeComponent
import letters.game.feautures.home.ui.HomeComponent
import letters.game.feautures.splash.createSplashComponent
import letters.game.feautures.splash.ui.SplashComponent

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
        initialConfiguration = ChildConfig.Splash,
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
            RootComponent.Child.Home(
                componentFactory.createHomeComponent(
                    componentContext,
                    ::onHomeOutput
                )
            )
        }

        ChildConfig.Splash -> {
            RootComponent.Child.Splash(
                componentFactory.createSplashComponent(
                    componentContext,
                    ::onSplashOutput
                )
            )
        }

        is ChildConfig.Game -> {
            RootComponent.Child.Game(
                componentFactory.createGameComponent(
                    componentContext,
                    config.id,
                    ::onGameOutput
                )
            )
        }
    }

    private fun onHomeOutput(output: HomeComponent.Output) {
        when (output) {
            is HomeComponent.Output.StartGame -> {
                navigation.safePush(ChildConfig.Game(output.gameId))
            }
        }
    }

    private fun onSplashOutput(output: SplashComponent.Output) {
        when (output) {
            is SplashComponent.Output.ContinueGame -> {
                navigation.safePush(ChildConfig.Game(output.game.id))
            }

            SplashComponent.Output.Home -> {
                navigation.safePush(ChildConfig.Home)
            }
        }
    }

    private fun onGameOutput(output: GameComponent.Output) {
        when (output) {
            GameComponent.Output.Home -> {
                navigation.safePush(ChildConfig.Home)
            }
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object Splash : ChildConfig

        @Parcelize
        object Home : ChildConfig

        @Parcelize
        data class Game(val id: GameId) : ChildConfig
    }
}