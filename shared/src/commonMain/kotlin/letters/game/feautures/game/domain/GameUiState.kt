package letters.game.feautures.game.domain

sealed interface GameUiState {
    data class Win(val attemptsCount: Int) : GameUiState

    object Playing : GameUiState

    data class Lose(val attemptsCount: Int) : GameUiState
}