package letters.game.feautures.splash

import com.arkivanov.decompose.ComponentContext
import letters.game.core.ComponentFactory
import letters.game.core.network.NetworkApiFactory
import letters.game.feautures.home.ui.HomeComponent
import letters.game.feautures.home.ui.RealHomeComponent
import letters.game.feautures.splash.data.DeviceApi
import letters.game.feautures.splash.data.DeviceRepository
import letters.game.feautures.splash.data.DeviceRepositoryImpl
import letters.game.feautures.splash.ui.RealSplashComponent
import letters.game.feautures.splash.ui.SplashComponent
import org.koin.core.component.get
import org.koin.dsl.module

val splashModule =  module {
    single { get<NetworkApiFactory>().authorizedKtorfit.create<DeviceApi>() }
    single<DeviceRepository> { DeviceRepositoryImpl(get(), get(), get()) }
}

fun ComponentFactory.createSplashComponent(
    componentContext: ComponentContext,
    onOutput: (SplashComponent.Output) -> Unit
): SplashComponent {
    val deviceReplica = get<DeviceRepository>().deviceReplica
    return RealSplashComponent(
        componentContext,
        onOutput,
        deviceReplica,
        get()
    )
}