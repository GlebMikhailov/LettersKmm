package letters.game.feautures.game.domain

sealed interface LetterState {
    companion object {
        private val MOCK_VALID = "sleep"
        val MOCK_VALID_WORD = MOCK_VALID.getLetterState(MOCK_VALID)
        val MOCK_INVALID_WORD = "arrow".getLetterState(MOCK_VALID)
        val MOCK_INVALID_POS = "sheep".getLetterState(MOCK_VALID)
    }
    val symbol: String

    data class Input(override val symbol: String) : LetterState
    data class InvalidPosition(override val symbol: String) : LetterState
    data class Success(override val symbol: String) : LetterState
    data class Error(override val symbol: String) : LetterState
}


fun String.getLetterState(originWord: String): List<LetterState> {
    val result = mutableListOf<LetterState>()
    forEachIndexed { index, symbol ->
        val state = when {
            symbol == originWord[index] -> {
                LetterState.Success(symbol.toString())
            }

            symbol in originWord && symbol != originWord[index]-> {
                LetterState.InvalidPosition(symbol.toString())
            }

            else -> {
                LetterState.Error(symbol.toString())
            }
        }
        result.add(state)
    }
    return result.toList()
}