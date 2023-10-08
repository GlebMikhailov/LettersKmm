package letters.game.features.game

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import letters.game.feautures.game.domain.Field
import letters.game.feautures.game.domain.LetterState
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.toCompose

@Composable
fun Field(
    modifier: Modifier = Modifier,
    inputControl: InputControl,
    lettersState: List<List<LetterState>>,
    field: Field,
    currentEditingRow: Int,
    currentEditingCell: Int
) {
    val letterWidth = LocalConfiguration.current.screenWidthDp.dp
    val divider = maxOf(lettersState.size, lettersState.first().size)

    val itemSize = (letterWidth - (16.dp * 2)) / divider
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(field.realHeight) { index ->
            FieldRowUi(
                modifier = Modifier.fillMaxWidth(),
                inputControl = inputControl,
                lettersState = lettersState[index],
                itemSize = itemSize,
                wordLength = field.width,
                hasFocus = currentEditingRow == index,
                currentEditingCell = currentEditingCell
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
    itemSize: Dp,
    wordLength: Int,
    hasFocus: Boolean,
    currentEditingCell: Int
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    val inputText by inputControl.text.collectAsState()

    val textFieldValueState by remember {
        derivedStateOf {
            TextFieldValue(
                text = inputText,
                selection = TextRange(inputText.length)
            )
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
                inputControl.onTextChanged(it.text)
            },
            keyboardOptions = inputControl.keyboardOptions.toCompose(),
            decorationBox = {
                DecorationBox(
                    modifier = Modifier.size(itemSize),
                    maxLength = wordLength,
                    text = textFieldValueState.text,
                    hasFocus = hasFocus,
                    lettersState = lettersState,
                    currentEditingCell = currentEditingCell
                )
            },
            enabled = false
        )
    }
}

@Composable
private fun DecorationBox(
    modifier: Modifier = Modifier,
    maxLength: Int,
    text: String,
    hasFocus: Boolean,
    lettersState: List<LetterState>,
    currentEditingCell: Int
) {
    Row(horizontalArrangement = Arrangement.Center) {
        repeat(maxLength) { index ->
            Letter(
                modifier = modifier,
                index = index,
                text = text,
                hasFocus = hasFocus && currentEditingCell == index,
                letterState = lettersState[index]
            )
            if (index != maxLength - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}