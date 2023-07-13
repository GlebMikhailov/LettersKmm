package letters.game.core

import letters.game.core.configuration.BuildType
import letters.game.core.configuration.Configuration
import letters.game.core.error_handling.ErrorHandler
import letters.game.core.network.BackendUrl
import letters.game.core.network.NetworkApiFactory
import letters.game.core.network.createDefaultJson
import me.aartikov.replica.client.ReplicaClient
import org.koin.core.module.Module
import org.koin.dsl.module
import letters.game.core.message.data.MessageService
import letters.game.core.message.data.MessageServiceImpl

fun commonCoreModule(configuration: Configuration) = module {
    single { configuration }
    single { ReplicaClient(getOrNull()) }
//    single { PagedLoadingClient() }
    single<MessageService> { MessageServiceImpl() }
    single { ErrorHandler(get()) }
    single {
        NetworkApiFactory(
            loggingEnabled = configuration.buildType == BuildType.Debug,
            backendUrl = BackendUrl.getMainUrl(configuration.backend),
            httpClientEngine = get(),
            configuration = configuration,
        )
    }
    factory { createDefaultJson() }
}

expect fun platformCoreModule(configuration: Configuration): Module