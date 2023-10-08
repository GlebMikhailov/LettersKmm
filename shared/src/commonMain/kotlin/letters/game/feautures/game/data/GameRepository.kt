package letters.game.feautures.game.data

import letters.game.feautures.game.data.dto.GameRequest
import letters.game.feautures.game.data.dto.GameResponse
import letters.game.feautures.game.domain.Game
import letters.game.feautures.game.domain.GameId
import me.aartikov.replica.keyed.KeyedReplica

interface GameRepository {
    val gameReplica: KeyedReplica<GameId, Game>

    suspend fun createGame(
        gameRequest: GameRequest
    ): GameResponse

    suspend fun createAttempt(
        gameId: GameId,
        word: String
    ): GameResponse

    suspend fun getMoreAttempts(gameId: GameId, ): GameResponse
}