package letters.game.feautures.home.ui

import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import letters.game.MR
import letters.game.core.error_handling.ErrorHandler
import letters.game.core.error_handling.safeLaunch
import letters.game.core.utils.componentScope
import letters.game.feautures.game.data.GameRepository
import letters.game.feautures.game.data.dto.GameRequest
import letters.game.feautures.game.domain.Field
import letters.game.feautures.game.domain.GameId
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.kmm_form_validation.options.KeyboardType

class RealHomeComponent(
    componentContext: ComponentContext,
    private val gameRepository: GameRepository,
    private val errorHandler: ErrorHandler,
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
    override val field: Field = Field(
        width = WORD_LEN,
        height = commonFieldData.size,
        realHeight = commonFieldData.size
    )

    init {
        wordInputControl.enabled.value = false
    }

    override fun onStartGameClick() {
        componentScope.safeLaunch(errorHandler) {
            val game = gameRepository.createGame(GameRequest(5, "ru"))
            onOutput(HomeComponent.Output.StartGame(GameId(game.id)))
        }
    }
}