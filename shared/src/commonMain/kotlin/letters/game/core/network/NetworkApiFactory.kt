package letters.game.core.network

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import letters.game.core.configuration.Configuration

/**
 * Creates implementations of Ktorfit APIs.
 */
class NetworkApiFactory(
    private val loggingEnabled: Boolean,
    private val backendUrl: BackendUrl,
    private val httpClientEngine: HttpClientEngine,
    private val configuration: Configuration
) {
    companion object {
        private const val CONNECT_TIMEOUT_MILLISECONDS = 30000L
        private const val READ_WRITE_TIMEOUT_MILLISECONDS = 60000L
        private const val API_KEY_HEADER = "apikey"
        private const val API_KEY = "34979cc742738eaad6fd30efe501648c"
    }

    private val json = createDefaultJson()
    private val authorizedHttpClient by lazy { createHttpClient(authorized = true) }
    private val unauthorizedHttpClient = createHttpClient(authorized = false)

    /**
     * Ktorfit for creating of authorized APIs
     */
    val authorizedKtorfit by lazy { createKtorfit(authorizedHttpClient) }

    /**
     * Ktorfit for creating of APIs that don't require authorization
     */
    val unauthorizedKtorfit = createKtorfit(unauthorizedHttpClient)

    private fun createHttpClient(authorized: Boolean): HttpClient {
        return HttpClient(httpClientEngine) {
            install(Logging) {
                logger = createKtorLogger()
                level = if (loggingEnabled) LogLevel.ALL else LogLevel.NONE
            }

            install(ContentNegotiation) {
                json(json)
            }

            install(HttpTimeout) {
                connectTimeoutMillis = CONNECT_TIMEOUT_MILLISECONDS
                requestTimeoutMillis = READ_WRITE_TIMEOUT_MILLISECONDS
            }

            defaultRequest {
                url(backendUrl.value)
                header(API_KEY_HEADER, API_KEY)
                contentType(ContentType.Application.Json)
            }

            setupErrorConverter()
        }
    }

    private fun createKtorfit(httpClient: HttpClient): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl(backendUrl.value)
            .httpClient(httpClient)
            .build()
    }
}