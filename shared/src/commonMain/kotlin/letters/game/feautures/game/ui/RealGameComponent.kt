package letters.game.feautures.game.ui

import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.CoroutineScope
import letters.game.MR
import letters.game.core.dialog.DialogControl
import letters.game.core.dialog.dialogControl
import letters.game.core.error_handling.ErrorHandler
import letters.game.core.error_handling.WordNotFoundException
import letters.game.core.error_handling.safeLaunch
import letters.game.core.message.data.MessageService
import letters.game.core.message.domain.Message
import letters.game.core.services.AdResult
import letters.game.core.services.AdService
import letters.game.core.state.CMutableStateFlow
import letters.game.core.state.CStateFlow
import letters.game.core.utils.LoadableState
import letters.game.core.utils.componentScope
import letters.game.core.utils.observe
import letters.game.feautures.game.data.GameRepository
import letters.game.feautures.game.domain.Game
import letters.game.feautures.game.domain.GameUiState
import letters.game.feautures.game.domain.LetterAction
import letters.game.feautures.game.domain.LetterState
import letters.game.feautures.game.domain.getLetterState
import letters.game.feautures.game.domain.isEditing
import letters.game.feautures.game.domain.toRequest
import letters.game.feautures.game.ui.game_result.DialogAction.GetMoreAttempts
import letters.game.feautures.game.ui.game_result.DialogAction.GoToMainMenu
import letters.game.feautures.game.ui.game_result.DialogAction.Restart
import letters.game.feautures.game.ui.game_result.GameResultDialogComponent
import letters.game.feautures.game.ui.game_result.RealGameResultDialogComponent
import me.aartikov.replica.single.Replica
import ru.mobileup.kmm_form_validation.control.InputControl

class RealGameComponent(
    componentContext: ComponentContext,
    private val gameReplica: Replica<Game>,
    private val errorHandler: ErrorHandler,
    private val appCoroutineScope: CoroutineScope,
    private val gameRepository: GameRepository,
    private val messageService: MessageService,
    private val adService: AdService,
    private val onOutput: (GameComponent.Output) -> Unit
) : GameComponent, ComponentContext by componentContext {

    override val currentEditingRow = CMutableStateFlow(-1)
    override val currentEditingCell = CMutableStateFlow(-1)
    override val gameUiState: CMutableStateFlow<GameUiState> =
        CMutableStateFlow(GameUiState.Playing)
    override val gameResultDialogControl: DialogControl<GameResultDialogComponent.DialogConfig, GameResultDialogComponent> =
        dialogControl(::createGameDialogComponent)

    override val gameResultDialogsInfo = GameResultDialogTypes(
        lose = GameResultDialogData(
            title = MR.strings.game_dialog_lose_title.desc(),
            positiveText = MR.strings.game_dialog_lose_positive_btn_text.desc(),
            negativeText = MR.strings.game_dialog_lose_negative_btn_text.desc(),
        ),
        win = GameResultDialogData(
            title = MR.strings.game_dialog_win_title.desc(),
            positiveText = MR.strings.game_dialog_win_positive_btn_text.desc(),
            negativeText = MR.strings.game_dialog_win_negative_btn_text.desc()
        )
    )

    override val game: CStateFlow<LoadableState<Game>> =
        gameReplica.observe(componentContext, errorHandler)

    override val wordInputControl: InputControl =
        InputControl(coroutineScope = componentContext.componentScope)

    override var lettersState: CMutableStateFlow<List<List<LetterState>>> =
        CMutableStateFlow(emptyList())

    init {
        appCoroutineScope.safeLaunch(
            errorHandler = errorHandler,
            block = {
                wordInputControl.text.collect {
                    val game = game.value.data ?: return@collect
                    val newList = lettersState.value.map { oldList -> oldList.toMutableList() }
                        .toMutableList()
                    val inputList = LetterState.listOfInputStates(it)
                    currentEditingRow.value =
                        newList.indexOfFirst { it.any { it is LetterState.Empty || it is LetterState.Input } }
                    currentEditingCell.value = it.length
                    newList[currentEditingRow.value] =
                        (inputList + LetterState.createListOfStates(
                            game.field.width - inputList.size,
                            LetterState.Empty
                        )).toMutableList()

                    lettersState.value = newList
                    if (it.length == game.word.name.length) {
                        try {
                            gameRepository.createAttempt(game.id, wordInputControl.text.value)
                        } catch (e: WordNotFoundException) {
                            messageService.showMessage(Message(text = MR.strings.game_word_not_found.desc()))
                            val newErrorList =
                                lettersState.value.map { oldList -> oldList.toMutableList() }
                                    .toMutableList()
                            wordInputControl.onTextChanged("")
                            newErrorList[currentEditingRow.value] =
                                LetterState.createListOfStates(game.field.width, LetterState.Empty)
                                    .toMutableList()
                            lettersState.value = newErrorList
                            checkState()
                        }
                        wordInputControl.onTextChanged("")
                    }
                }
            })

        appCoroutineScope.safeLaunch(errorHandler) {
            game.collect {
                val game = game.value.data ?: return@collect
                val finalList = game.attempts.map {
                    it.word.name.getLetterState(game.word.name)
                }.toMutableList()
                currentEditingRow.value = finalList.size
                currentEditingCell.value = 0
                if (finalList.size < game.field.realHeight) {
                    repeat(game.field.realHeight - finalList.size) {
                        finalList.add(
                            LetterState.createListOfStates(
                                game.field.width,
                                LetterState.Empty
                            )
                        )
                    }
                }
                lettersState.value = finalList
                checkState()
            }
        }

        appCoroutineScope.safeLaunch(errorHandler) {
            gameUiState.collect {
                when (it) {
                    is GameUiState.Lose, is GameUiState.Win -> {
                        gameResultDialogControl.show(GameResultDialogComponent.DialogConfig)
                    }
                    GameUiState.Playing -> Unit
                }
            }
        }
    }

    override fun onRefresh() {
        gameReplica.refresh()
    }

    override fun onRetryClick() {
        gameReplica.refresh()
    }

    override fun onDialogDismiss() {
        gameResultDialogControl.dismiss()
    }

    override fun onLetterClick(action: LetterAction) {
        appCoroutineScope.safeLaunch(errorHandler) {
            val currentText = wordInputControl.text.value
            when (action) {
                is LetterAction.AddLetter -> wordInputControl.onTextChanged(currentText + action.letter.name)
                LetterAction.Clear -> wordInputControl.onTextChanged(currentText.dropLast(1))
            }
        }
    }

    private fun checkState() {
        val attemptsCount = lettersState.value.count { it.all { !it.isEditing } }
        gameUiState.value = when (lettersState.value.last().last()) {
            LetterState.Empty, is LetterState.Input -> GameUiState.Playing
            is LetterState.Error, is LetterState.InvalidPosition -> GameUiState.Lose(attemptsCount)
            is LetterState.Success -> GameUiState.Win(attemptsCount)
        }
    }

    private fun createGameDialogComponent(
        config: GameResultDialogComponent.DialogConfig,
        componentContext: ComponentContext,
        dialogControl: DialogControl<GameResultDialogComponent.DialogConfig, GameResultDialogComponent>
    ): GameResultDialogComponent {
        return RealGameResultDialogComponent(
            gameUiState.value,
            dialogControl = dialogControl,
            onAction = {
                val game = game.value.data ?: return@RealGameResultDialogComponent
                appCoroutineScope.safeLaunch(errorHandler) {
                    when (it) {
                        GetMoreAttempts -> {
                            when (adService.showAd()) {
                                AdResult.Canceled -> {

                                }

                                AdResult.Failed -> {

                                }

                                AdResult.Watched -> {
                                    gameRepository.createAttempt(game.id, wordInputControl.text.value)
                                }
                            }
                        }

                        GoToMainMenu -> {
                            onOutput(GameComponent.Output.Home)
                        }

                        Restart -> {
                            gameRepository.createGame(game.toRequest())
                        }
                    }
                }
            })
    }
}