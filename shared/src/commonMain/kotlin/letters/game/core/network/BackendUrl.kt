package letters.game.core.network

import letters.game.core.configuration.Backend

data class BackendUrl(val value: String) {

    companion object {
        private val MainDevelopmentUrl = BackendUrl("http://192.168.10.25:8081/")
        private val MainProductionUrl = BackendUrl("http://192.168.10.25:8081/")

        fun getMainUrl(backend: Backend) = when (backend) {
            Backend.Development -> MainDevelopmentUrl
            Backend.Production -> MainProductionUrl
        }
    }
}