package letters.game.feautures.game.ui

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import letters.game.MR
import letters.game.core.error_handling.ErrorHandler
import letters.game.core.error_handling.safeLaunch
import letters.game.core.state.CStateFlow
import letters.game.core.utils.LoadableState
import letters.game.core.utils.componentScope
import letters.game.core.utils.formValidator
import letters.game.feautures.game.domain.Game
import me.aartikov.replica.single.Replica
import letters.game.core.utils.observe
import letters.game.feautures.game.data.GameRepository
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.kmm_form_validation.options.KeyboardType
import ru.mobileup.kmm_form_validation.validation.control.minLength
import ru.mobileup.kmm_form_validation.validation.form.FormValidator
import ru.mobileup.kmm_form_validation.validation.form.RevalidateOnValueChanged
import ru.mobileup.kmm_form_validation.validation.form.ValidateOnFocusLost

class RealGameComponent(
    componentContext: ComponentContext,
    gameReplica: Replica<Game>,
    private val errorHandler: ErrorHandler,
    private val appCoroutineScope: CoroutineScope,
    private val gameRepository: GameRepository
) : GameComponent, ComponentContext by componentContext {

    override val game: CStateFlow<LoadableState<Game>> = gameReplica.observe(componentContext, errorHandler)

    override val wordInputControl: InputControl = InputControl(
        coroutineScope = componentContext.componentScope,
        initialText = "",
        maxLength = game.value.data?.word?.name?.length ?: 0,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
    )

    private val formValidator: FormValidator = formValidator {
        input(wordInputControl) {
            minLength(game.value.data?.word?.name?.length ?: 0, MR.strings.error_invalid_response)
        }
    }

    override fun onFinishInput(word: String) {
        val gameId = game.value.data?.id ?: return
        appCoroutineScope.safeLaunch(errorHandler) {
            if (formValidator.validate(false).isValid) {
                gameRepository.createAttempt(gameId, wordInputControl.text.value)
            }
        }
    }
}