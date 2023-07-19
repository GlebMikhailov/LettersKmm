package letters.game.features.game

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.localized
import kotlinx.coroutines.launch
import letters.game.core.theme.AppTheme
import letters.game.feautures.game.domain.LetterState
import letters.game.feautures.game.domain.getLetterState
import letters.game.feautures.game.ui.GameComponent
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.toCompose

@Composable
fun GameUi(component: GameComponent) {
    Field(
        lettersState = commonField.map { it.localized().getLetterState(commonField.last().localized()) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        inputControl = component.wordInputControl,
        onUpdatingLetterState = {  }
    )
}

@Composable
fun Field(modifier: Modifier = Modifier, inputControl: InputControl, lettersState: List<List<LetterState>>, onUpdatingLetterState: (String) -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        lettersState.forEachIndexed { index, s ->
            FieldRowUi(
                inputControl = inputControl,
                lettersState = lettersState[index],
                onUpdatingLetterState = onUpdatingLetterState
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun FieldRowUi(
    modifier: Modifier = Modifier,
    inputControl: InputControl,
    lettersState: List<LetterState>,
    onUpdatingLetterState: (String) -> Unit,
) {
    val letterWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemSize = (letterWidth - (16.dp * 2)) / lettersState.size

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val inputText by inputControl.text.collectAsState()
    val inputError by inputControl.error.collectAsState()
    val hasFocus by inputControl.hasFocus.collectAsState()
    val highlightCurrentCell = hasFocus || inputText.isNotEmpty()

    val textFieldValueState by remember {
        derivedStateOf {
            TextFieldValue(
                text = inputText,
                selection = TextRange(inputText.length)
            )
        }
    }

    SideEffect {
        if (hasFocus) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        keyboard?.show()
                        coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                    }
                    inputControl.onFocusChanged(focusState.isFocused)
                },
            value = textFieldValueState,
            onValueChange = {
                if (it.text.length == inputControl.maxLength) {
                    onUpdatingLetterState(it.text)
                }
                inputControl.onTextChanged(it.text)
            },
            keyboardOptions = inputControl.keyboardOptions.toCompose(),
            decorationBox = {
                DecorationBox(
                    modifier = Modifier.size(itemSize),
                    maxLength = inputControl.maxLength,
                    text = textFieldValueState.text,
                    hasFocus = highlightCurrentCell,
                    lettersState = lettersState
                )
            }
        )
    }
}

@Composable
private fun DecorationBox(
    modifier: Modifier = Modifier,
    maxLength: Int,
    text: String,
    hasFocus: Boolean,
    lettersState: List<LetterState>
) {
    Row(horizontalArrangement = Arrangement.Center) {
        repeat(maxLength) { index ->
            Letter(
                modifier = modifier,
                index = index,
                text = text,
                hasFocus = hasFocus,
                letterState = lettersState[index]
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FieldRowUiPreview() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//            FieldRowUi("текст".getLetterState("башня"))
//            FieldRowUi("драко".getLetterState("кость"))
//            FieldRowUi("синий".getLetterState("синий"))
        }
    }
}