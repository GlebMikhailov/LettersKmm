package letters.game.feautures.splash.ui

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import letters.game.core.error_handling.ErrorHandler
import letters.game.core.state.CStateFlow
import letters.game.core.utils.LoadableState
import letters.game.core.utils.componentScope
import letters.game.core.utils.observe
import letters.game.feautures.splash.domain.Device
import me.aartikov.replica.single.Replica

class RealSplashComponent(
    componentContext: ComponentContext,
    private val onOutput: (SplashComponent.Output) -> Unit,
    deviceReplica: Replica<Device>,
    errorHandler: ErrorHandler
) : SplashComponent, ComponentContext by componentContext {

    override val deviceState: CStateFlow<LoadableState<Device>> = deviceReplica.observe(this, errorHandler)

    init {
        deviceState.onEach {
            if (it.data != null) {
                if (it.data.lastGame == null) {
                    onOutput(SplashComponent.Output.Home)
                } else {
                    onOutput(SplashComponent.Output.ContinueGame(it.data.lastGame))
                }
            }
        }.launchIn(componentScope)
    }
}