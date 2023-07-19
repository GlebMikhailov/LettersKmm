package letters.game.feautures.game.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameRequest(
    @SerialName("wordLength") val wordLength: Int,
    @SerialName("isoCode2") val language: String,
)

@Serializable
data class AttemptRequest(
    @SerialName("word") val word: String,
)