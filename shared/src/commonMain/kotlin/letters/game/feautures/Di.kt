package letters.game.feautures

import letters.game.core.configuration.Configuration
import letters.game.feautures.game.gameModule
import letters.game.feautures.home.homeModule
import letters.game.feautures.root.rootModule
import letters.game.feautures.splash.splashModule

fun featuresModules(configuration: Configuration) = listOf(
    rootModule,
    splashModule(configuration),
    gameModule,
    homeModule
)