package letters.game.feautures.game.data

import letters.game.core.error_handling.WordNotFoundException
import letters.game.core.network.ReplicaTags
import letters.game.feautures.game.data.dto.AttemptRequest
import letters.game.feautures.game.data.dto.GameRequest
import letters.game.feautures.game.data.dto.GameResponse
import letters.game.feautures.game.data.dto.toGame
import letters.game.feautures.game.domain.Game
import letters.game.feautures.game.domain.GameId
import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.keyed.KeyedReplica
import me.aartikov.replica.keyed.KeyedReplicaSettings
import me.aartikov.replica.single.ReplicaSettings
import kotlin.time.Duration.Companion.minutes

class GameRepositoryImpl(
    replicaClient: ReplicaClient,
    private val gameApi: GameApi,
) : GameRepository {

    override val gameReplica: KeyedReplica<GameId, Game> = replicaClient.createKeyedReplica(
        name = "game",
        childName = { "gameId = ${it.value}" },
        settings = KeyedReplicaSettings(maxCount = 10),
        tags = setOf(ReplicaTags.Game),
        childSettings = {
            ReplicaSettings(staleTime = 1.minutes, clearTime = 30.minutes)
        },
        fetcher = { gameId ->
            gameApi.getGame(gameId.value).data!!.toGame()
        }
    )

    override suspend fun createGame(gameRequest: GameRequest): GameResponse {
        val response = gameApi.createGame(gameRequest).data!!
        gameReplica.refresh(GameId(response.id))
        return response
    }

    override suspend fun createAttempt(gameId: GameId, word: String): GameResponse {
        val response = gameApi.createAttempt(gameId.value, AttemptRequest(word)).data ?: throw WordNotFoundException(word)
        gameReplica.refresh(GameId(response.id))
        return response
    }

    override suspend fun getMoreAttempts(gameId: GameId): GameResponse {
        val response = gameApi.getMoreAttempts(gameId.value).data!!
        gameReplica.refresh(GameId(response.id))
        return response
    }
}