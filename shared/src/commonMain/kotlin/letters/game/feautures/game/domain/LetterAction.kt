package letters.game.feautures.game.domain

sealed interface LetterAction {
    data class AddLetter(val letter: Letter) : LetterAction
    object Clear : LetterAction
}
