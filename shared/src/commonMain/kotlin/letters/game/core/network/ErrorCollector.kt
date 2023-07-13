package letters.game.core.network

import letters.game.core.error_handling.ApplicationException

fun interface ErrorCollector {
    fun collectNetworkError(exception: ApplicationException)
}