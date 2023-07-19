package letters.game.feautures.home.ui

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.MainScope
import letters.game.MR
import letters.game.feautures.game.domain.LetterState
import ru.mobileup.kmm_form_validation.control.InputControl

class FakeHomeComponent : HomeComponent {
    override val commonFieldData: List<StringDesc> = listOf(
        MR.strings.home_game_example1.desc(),
        MR.strings.home_game_example2.desc(),
        MR.strings.home_game_example3.desc()
    )
    override val wordInputControl: InputControl = InputControl(MainScope())

    override fun onStartGameClick() = Unit
}