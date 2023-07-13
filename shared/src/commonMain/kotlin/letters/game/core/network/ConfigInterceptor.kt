package letters.game.core.network

import io.ktor.client.call.HttpClientCall
import io.ktor.client.plugins.Sender
import io.ktor.client.request.HttpRequestBuilder
import letters.game.core.configuration.Configuration
import letters.game.core.configuration.PlatformType

class ConfigInterceptor(
    private val configuration: Configuration,
) {

    suspend fun intercept(sender: Sender, request: HttpRequestBuilder): HttpClientCall {
        request.attachConfig(configuration)
        return sender.execute(request)
    }

    private fun HttpRequestBuilder.attachConfig(configuration: Configuration) {
        return with(this.url) {
            parameters[DEVICE_PARAMETER_NAME] = configuration.platform.type.toDto()
            parameters[VERSION_CODE_PARAMETER_NAME] = configuration.appVersionCode.toString()
        }
    }

    private fun PlatformType.toDto(): String {
        return when (this) {
            PlatformType.Android -> "android"
            PlatformType.Ios -> "ios"
        }
    }

    companion object {
        private const val VERSION_CODE_PARAMETER_NAME = "version"
        private const val DEVICE_PARAMETER_NAME = "device"
    }
}