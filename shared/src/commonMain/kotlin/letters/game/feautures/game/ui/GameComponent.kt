package letters.game.feautures.game.ui

import letters.game.core.state.CStateFlow
import letters.game.core.utils.LoadableState
import letters.game.feautures.game.domain.Game
import ru.mobileup.kmm_form_validation.control.InputControl

interface GameComponent {

    val game: CStateFlow<LoadableState<Game>>

    val wordInputControl: InputControl
    fun onFinishInput(word: String)
}