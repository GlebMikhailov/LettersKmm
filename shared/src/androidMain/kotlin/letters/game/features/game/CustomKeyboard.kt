package letters.game.features.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import letters.game.MR
import letters.game.core.utils.toPx
import letters.game.core.widgets.LetterButton
import letters.game.feautures.game.domain.Letter
import letters.game.feautures.game.domain.LetterAction

@Composable
fun CustomKeyboard(
    modifier: Modifier = Modifier,
    keyboardLetters: List<List<Letter>>,
    onLetterClick: (LetterAction) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val totalMaxSize = keyboardLetters.maxByOrNull { it.size }!!.size
    val letterWidth =
        (screenWidth - 32.dp - (totalMaxSize * LETTERS_DIVIDER).dp).value.toInt() / totalMaxSize
    var clearButtonWidth by remember { mutableStateOf(0.dp) }
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        keyboardLetters.forEachIndexed { index, letters ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(LETTERS_DIVIDER.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.onGloballyPositioned {
                    if (index == keyboardLetters.size - 1) {
                        clearButtonWidth = it.size.width.toPx.dp
                    }
                }
            ) {
                letters.forEach { letter ->
                    LetterButton(
                        letter = letter,
                        width = letterWidth.dp,
                        height = (letterWidth * 1.7).dp,
                        secondHeight = ((letterWidth * 1.7) / 7).dp
                    ) {
                        onLetterClick(LetterAction.AddLetter(letter))
                    }
                }
            }
        }
        LetterButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = MR.strings.game_clear.resourceId),
            width = clearButtonWidth,
            height = (letterWidth * 1.7).dp,
            secondHeight = ((letterWidth * 1.7) / 7).dp
        ) {
            onLetterClick(LetterAction.Clear)
        }
    }
}

private const val LETTERS_DIVIDER = 2