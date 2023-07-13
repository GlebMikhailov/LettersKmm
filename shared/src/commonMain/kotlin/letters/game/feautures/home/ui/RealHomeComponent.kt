package letters.game.feautures.home.ui

import com.arkivanov.decompose.ComponentContext
import letters.game.feautures.game.domain.GameId

class RealHomeComponent(
    componentContext: ComponentContext,
    private val onOutput: (HomeComponent.Output) -> Unit
) : HomeComponent, ComponentContext by componentContext {
    override fun onStartGameClick() {
        onOutput(HomeComponent.Output.StartGame(GameId(-1)))
    }
}