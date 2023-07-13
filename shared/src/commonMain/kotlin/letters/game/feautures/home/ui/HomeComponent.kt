package letters.game.feautures.home.ui

import letters.game.feautures.game.domain.GameId

interface HomeComponent {
    fun onStartGameClick()

    sealed interface Output {
        data class StartGame(val gameId: GameId) : Output
    }
}