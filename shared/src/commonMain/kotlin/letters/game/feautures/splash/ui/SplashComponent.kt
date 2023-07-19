package letters.game.feautures.splash.ui

import letters.game.core.state.CStateFlow
import letters.game.core.utils.LoadableState
import letters.game.feautures.game.domain.Game
import letters.game.feautures.splash.domain.Device

interface SplashComponent {

    val deviceState: CStateFlow<LoadableState<Device>>

    sealed interface Output {
        data class ContinueGame(val game: Game) : Output

        object Home : Output
    }
}