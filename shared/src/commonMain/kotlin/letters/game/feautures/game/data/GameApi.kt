package letters.game.feautures.game.data

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import letters.game.feautures.game.data.dto.AttemptRequest
import letters.game.feautures.game.data.dto.GameRequest
import letters.game.feautures.game.data.dto.GameResponse
import letters.game.feautures.splash.data.dto.DeviceRequest
import letters.game.feautures.splash.data.dto.DeviceResponse


interface GameApi {
    companion object {
        private const val GET_GAME = "{gameId}"
        private const val CREATE_GAME = "game"
        private const val CREATE_ATTEMPT = "{gameId}/attempt"
    }

    @GET(GET_GAME)
    suspend fun getGame(
        @Path("gameId") gameId: Long,
    ): GameResponse

    @POST(CREATE_GAME)
    suspend fun createGame(
        @Body gameRequest: GameRequest
    ): GameResponse

    @POST(CREATE_ATTEMPT)
    suspend fun createAttempt(
        @Path("gameId") gameId: Long,
        @Body attemptRequest: AttemptRequest
    ): GameResponse
}