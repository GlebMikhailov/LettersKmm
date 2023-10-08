package letters.game.feautures.game.domain

sealed interface LetterState {
    companion object {
        private val MOCK_VALID = "sleep"
        val MOCK_VALID_WORD = MOCK_VALID.getLetterState(MOCK_VALID)
        val MOCK_INVALID_WORD = "arrow".getLetterState(MOCK_VALID)
        val MOCK_INVALID_POS = "sheep".getLetterState(MOCK_VALID)

        fun createListOfStates(size: Int, state: LetterState): List<LetterState> {
            val result = mutableListOf<LetterState>()
            repeat(size) {
                result.add(state)
            }
            return result
        }

        fun listOfInputStates(word: String): MutableList<LetterState> {
            val result = mutableListOf<LetterState>()
            word.forEach {
                result.add(Input(it.toString()))
            }
            return result
        }
    }

    val symbol: String

    data class Input(override val symbol: String) : LetterState
    data class InvalidPosition(override val symbol: String) : LetterState
    data class Success(override val symbol: String) : LetterState
    data class Error(override val symbol: String) : LetterState

    object Empty : LetterState {
        override val symbol: String = ""
    }
}

val LetterState.isEditing: Boolean
    get() = this == LetterState.Empty || this is LetterState.Input

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