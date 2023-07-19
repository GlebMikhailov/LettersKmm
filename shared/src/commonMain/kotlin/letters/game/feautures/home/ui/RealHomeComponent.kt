package letters.game.feautures.home.ui

import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import letters.game.MR
import letters.game.core.utils.componentScope
import letters.game.feautures.game.domain.GameId
import letters.game.feautures.game.domain.LetterState
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.kmm_form_validation.options.KeyboardType

class RealHomeComponent(
    componentContext: ComponentContext,
    private val onOutput: (HomeComponent.Output) -> Unit
) : HomeComponent, ComponentContext by componentContext {
    companion object {
        private const val WORD_LEN = 5
    }

    override val commonFieldData: List<StringDesc> = listOf(
        MR.strings.home_game_example1.desc(),
        MR.strings.home_game_example2.desc(),
        MR.strings.home_game_example3.desc(),
    )
    override val wordInputControl: InputControl = InputControl(
        coroutineScope = componentContext.componentScope,
        initialText = "",
        maxLength = WORD_LEN,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
    )

    init {
        wordInputControl.enabled.value = false
    }

    override fun onStartGameClick() {
        onOutput(HomeComponent.Output.StartGame(GameId(-1)))
    }
}