package letters.game.feautures.home

import com.arkivanov.decompose.ComponentContext
import letters.game.core.ComponentFactory
import letters.game.feautures.home.ui.HomeComponent
import letters.game.feautures.home.ui.RealHomeComponent
import org.koin.dsl.module

val homeModule = module {
    single { get<HomeComponent>() }
}

fun ComponentFactory.createHomeComponent(
    componentContext: ComponentContext,
    onOutput: (HomeComponent.Output) -> Unit
): HomeComponent {
    return RealHomeComponent(
        componentContext,
        onOutput
    )
}
