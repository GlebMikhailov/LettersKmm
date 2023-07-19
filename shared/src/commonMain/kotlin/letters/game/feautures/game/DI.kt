package letters.game.feautures.game

import com.arkivanov.decompose.ComponentContext
import letters.game.core.ComponentFactory
import letters.game.feautures.game.data.GameRepository
import letters.game.feautures.game.data.GameRepositoryImpl
import letters.game.feautures.game.domain.GameId
import letters.game.feautures.game.ui.GameComponent
import letters.game.feautures.game.ui.RealGameComponent
import me.aartikov.replica.algebra.withKey
import org.koin.core.component.get
import org.koin.dsl.module

val gameModule = module {
    single<GameRepository> { GameRepositoryImpl(get(), get()) }
}


fun ComponentFactory.createGameComponent(
    componentContext: ComponentContext,
    gameId: GameId
): GameComponent {
    val gameReplica = get<GameRepository>()
        .gameReplica
        .withKey(gameId)
    return RealGameComponent(componentContext, gameReplica)
}