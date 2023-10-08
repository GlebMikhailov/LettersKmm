package letters.game.feautures.game.data

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import letters.game.core.data.response.BaseResponse
import letters.game.feautures.game.data.dto.AttemptRequest
import letters.game.feautures.game.data.dto.GameRequest
import letters.game.feautures.game.data.dto.GameResponse


interface GameApi {
    companion object {
        private const val GET_GAME = "game/{gameId}"
        private const val CREATE_GAME = "game/game"
        private const val CREATE_ATTEMPT = "game/{gameId}/attempt"
    }

    @GET(GET_GAME)
    suspend fun getGame(
        @Path("gameId") gameId: Long,
    ): BaseResponse<GameResponse>

    @POST(CREATE_GAME)
    suspend fun createGame(
        @Body gameRequest: GameRequest
    ): BaseResponse<GameResponse>

    @POST(CREATE_ATTEMPT)
    suspend fun createAttempt(
        @Path("gameId") gameId: Long,
        @Body attemptRequest: AttemptRequest
    ): BaseResponse<GameResponse>

    @POST("game/{gameId}/additional_attempts")
    suspend fun getMoreAttempts(
        @Path("gameId") gameId: Long,
    ): BaseResponse<GameResponse>
}