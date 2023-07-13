package letters.game.core.error_handling

import kotlinx.serialization.Serializable

@Serializable
class ErrorResponse(val error: Error) {
    @Serializable
    class Error(val message: String)
}