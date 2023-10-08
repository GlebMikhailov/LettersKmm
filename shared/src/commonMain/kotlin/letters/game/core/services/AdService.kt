package letters.game.core.services

interface AdService {
    suspend fun showAd(): AdResult
}

sealed interface AdResult {
    object Watched : AdResult

    object Canceled : AdResult

    object Failed : AdResult
}