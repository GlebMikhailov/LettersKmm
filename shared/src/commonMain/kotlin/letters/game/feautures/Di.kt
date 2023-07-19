package letters.game.feautures

import letters.game.feautures.game.gameModule
import letters.game.feautures.home.homeModule
import letters.game.feautures.root.rootModule
import letters.game.feautures.splash.splashModule
import org.koin.dsl.module

val featuresModules = listOf(
    rootModule,
    splashModule,
    gameModule,
    homeModule
)