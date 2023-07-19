package letters.game.feautures.game.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import letters.game.feautures.game.domain.Attempt
import letters.game.feautures.game.domain.AttemptId
import letters.game.feautures.game.domain.Field
import letters.game.feautures.game.domain.Game
import letters.game.feautures.game.domain.GameId
import letters.game.feautures.game.domain.Language
import letters.game.feautures.game.domain.LanguageId
import letters.game.feautures.game.domain.Word
import letters.game.feautures.game.domain.WordId
import letters.game.feautures.splash.data.dto.DeviceResponse
import letters.game.feautures.splash.data.dto.toDevice

@Serializable
data class GameResponse(
    @SerialName("id") val id: Long,
    @SerialName("word") val word: WordResponse,
    @SerialName("field") val field: FieldResponse,
    @SerialName("attempts") val attempts: List<AttemptResponse>,
    @SerialName("device") val device: DeviceResponse
)

@Serializable
data class LanguageResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("nativeName") val nativeName: String,
    @SerialName("isoCode2") val isoCode2: String,
    @SerialName("isoCode3") val isoCode3: String,
    @SerialName("letters") val letters: List<String>,
)

@Serializable
data class FieldResponse(
    @SerialName("height") val height: Int,
    @SerialName("width") val width: Int,
)

@Serializable
data class AttemptResponse(
    @SerialName("id") val id: Long,
    @SerialName("word") val word: WordResponse,
    @SerialName("isSuccess") val isSuccess: Boolean,
    @SerialName("index") val index: Int
)

@Serializable
data class WordResponse(
    @SerialName("id") val id: Long,
    @SerialName("word") val word: String,
    @SerialName("language") val language: LanguageResponse,
)

fun LanguageResponse.toLanguage() = Language(
    id = LanguageId(id),
    name = name,
    nativeName = nativeName,
    isoCode2 = isoCode2,
    isoCode3 = isoCode3,
    letters = letters
)

fun FieldResponse.toField() = Field(
    width = width,
    height = height
)

fun AttemptResponse.toAttempt() = Attempt(
    id = AttemptId(id),
    word = word.toWord(),
    isSuccess = isSuccess,
    index = index
)


fun WordResponse.toWord() = Word(
    id = WordId(id),
    name = word,
    language = language.toLanguage()
)

fun GameResponse.toGame() = Game(
    id = GameId(id),
    word = word.toWord(),
    field = field.toField(),
    attempts = attempts.map { it.toAttempt() }
)