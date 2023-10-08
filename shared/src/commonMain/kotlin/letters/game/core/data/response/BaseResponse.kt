package letters.game.core.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: T?,
    @SerialName("type")
    val type: String,
)

enum class BaseResponseType {
    Success, Failed
}