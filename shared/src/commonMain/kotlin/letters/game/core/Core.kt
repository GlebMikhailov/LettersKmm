package letters.game.core

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import com.arkivanov.decompose.ComponentContext
import letters.game.core.configuration.BuildType
import letters.game.core.configuration.Configuration
import letters.game.feautures.featuresModules
import letters.game.feautures.root.createRootComponent
import org.koin.core.Koin
import letters.game.feautures.root.ui.RootComponent

private const val productVersionCode = 1
private const val productVersionName = "1.0"

class Core(config: Configuration) {

    val koin: Koin
    val configuration = config.copy(
        appVersionCode = config.appVersionCode + productVersionCode,
        appVersionName = "$productVersionName-${config.appVersionName}"
    )

    init {
        if (configuration.buildType == BuildType.Release) {
            Logger.setMinSeverity(Severity.Assert)
        }
        koin = createKoin(configuration)
    }

    fun createRootComponent(
        componentContext: ComponentContext,
        onCloseApp: () -> Unit
    ): RootComponent {
        return koin.get<ComponentFactory>().createRootComponent(componentContext, onCloseApp)
    }

    private fun createKoin(configuration: Configuration): Koin {
        return Koin().apply {
            loadModules(
                listOf(
                    commonCoreModule(configuration),
                    platformCoreModule(configuration),
                ) + featuresModules
            )
            declare(ComponentFactory(this))
            createEagerInstances()
        }
    }
}

interface CoreProvider {
    val core: Core
}