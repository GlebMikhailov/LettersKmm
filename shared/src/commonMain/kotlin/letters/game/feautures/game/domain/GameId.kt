package letters.game.feautures.game.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import letters.game.feautures.game.data.dto.GameRequest

@Parcelize
data class GameId(val value: Long) : Parcelable

@Parcelize
data class Game(
    val id: GameId,
    val word: Word,
    val attempts: List<Attempt>,
    val field: Field,
    val additionalAttempts: Int
) : Parcelable {
    companion object {
        val MOCK = Game(
            id = GameId(-1),
            word = Word.MOCK,
            attempts = emptyList(),
            field = Field.MOCK,
            additionalAttempts = 0
        )

        val MOCK_WITH_ATTEMPTS = Game(
            id = GameId(-1),
            word = Word.MOCK,
            attempts = listOf(Attempt.MOCK),
            field = Field.MOCK,
            additionalAttempts = 2
        )
    }
}

@Parcelize
data class Field(
    val width: Int,
    val height: Int,
    val realHeight: Int
) : Parcelable {
    companion object {
        val MOCK = Field(4, 5, 7)
    }
}

@Parcelize
data class AttemptId(
    val value: Long
) : Parcelable

@Parcelize
data class Attempt(
    val id: AttemptId,
    val word: Word,
    val isSuccess: Boolean,
    val index: Int
) : Parcelable {
    companion object {
        val MOCK = Attempt(
            id = AttemptId(-1),
            word = Word.MOCK,
            isSuccess = true,
            index = 0
        )
    }
}

@Parcelize
data class WordId(val value: Long) : Parcelable

@Parcelize
data class Word(
    val id: WordId,
    val name: String,
    val language: Language
) : Parcelable {
    companion object {
        val MOCK = Word(
            id = WordId(-1),
            name = "show",
            language = Language.MOCK
        )
    }
}

@Parcelize
data class LanguageId(
    val value: Int
) : Parcelable

@Parcelize
data class Language(
    val id: LanguageId,
    val name: String,
    val nativeName: String,
    val isoCode2: String,
    val isoCode3: String,
    val letters: List<List<Letter>> = emptyList(),
) : Parcelable {
    companion object {
        val MOCK = Language(
            id = LanguageId(-1),
            name = "English",
            nativeName = "English",
            isoCode2 = "en",
            isoCode3 = "eng"
        )
    }
}
@Parcelize
data class LetterId(
    val value: Int
) : Parcelable

@Parcelize
data class Letter(
    val id: LetterId,
    val name: String,
) : Parcelable {
    companion object {
        fun russianLetters(): List<List<Letter>> {
            val letters = listOf(
                listOf("й", "ц", "у", "к", "е", "н", "г", "ш", "щ", "з", "х", "ъ"),
                listOf("ф", "ы", "в", "а", "п", "р", "о", "л", "д", "ж", "э"),
                listOf("я", "ч", "с", "м", "и", "т", "ь", "б", "ю")
            )
            return letters.mapIndexed { indexParent, parentList ->
                parentList.mapIndexed { index, s ->
                    Letter(LetterId(index + indexParent), s)
                }
            }
        }
    }
}

fun Game.toRequest() = GameRequest(
    wordLength = word.name.length,
    language = word.language.isoCode2
)