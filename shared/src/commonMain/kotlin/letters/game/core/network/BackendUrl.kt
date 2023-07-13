package letters.game.core.network

import letters.game.core.configuration.Backend

data class BackendUrl(val value: String) {

    companion object {
        private val MainDevelopmentUrl = BackendUrl(TODO())
        private val MainProductionUrl = BackendUrl("http://127.0.0.1:8081")

        fun getMainUrl(backend: Backend) = when (backend) {
            Backend.Development -> MainDevelopmentUrl
            Backend.Production -> MainProductionUrl
        }
    }
}