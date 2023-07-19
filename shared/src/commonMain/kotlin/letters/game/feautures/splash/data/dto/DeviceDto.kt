package letters.game.feautures.splash.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import letters.game.feautures.game.data.dto.GameResponse
import letters.game.feautures.game.data.dto.toGame
import letters.game.feautures.splash.domain.Device

@Serializable
data class DeviceRequest(
    @SerialName("deviceId") val deviceId: String,
    @SerialName("model") val model: String,
    @SerialName("type") val type: String, // [ios, android]
    @SerialName("store") val store: String, // [google_play, app_store, app_gallery, none]
)

@Serializable
data class DeviceResponse(
    @SerialName("freeGames") val freeGames: Int,
    @SerialName("needWatchAd") val needWatchAd: Boolean,
    @SerialName("lastGame") val game: GameResponse?
)

fun DeviceResponse.toDevice() = Device.DEFAULT.copy(freeGames = freeGames, lastGame = game?.toGame())