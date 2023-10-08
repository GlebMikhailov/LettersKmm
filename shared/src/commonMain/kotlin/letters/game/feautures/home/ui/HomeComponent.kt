package letters.game.feautures.home.ui

import dev.icerock.moko.resources.desc.StringDesc
import letters.game.feautures.game.domain.Field
import letters.game.feautures.game.domain.GameId
import ru.mobileup.kmm_form_validation.control.InputControl

interface HomeComponent {

    val commonFieldData: List<StringDesc>
    val wordInputControl: InputControl

    val field: Field
    fun onStartGameClick()

    sealed interface Output {
        data class StartGame(val gameId: GameId) : Output
    }
}