package letters.game.core

import android.app.Application
import android.content.Context
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import letters.game.core.activity.ActivityProvider
import letters.game.core.configuration.Configuration
import letters.game.core.network.createOkHttpEngine
import letters.game.core.services.AdService
import letters.game.core.services.AndroidAdService
import org.koin.dsl.module

actual fun platformCoreModule(configuration: Configuration) = module {
    single { get<Configuration>().platform.application }
    single { get<Configuration>().platform.debugTools }
    single<Context> { get<Application>() }
    single { ActivityProvider() }
    single { createOkHttpEngine(get()) }
    single { PermissionsController(applicationContext = get()) }
    single<AdService> { AndroidAdService(get(), configuration, get(), get()) }
}