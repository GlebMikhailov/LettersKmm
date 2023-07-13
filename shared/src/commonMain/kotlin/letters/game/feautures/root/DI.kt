package letters.game.feautures.root

import com.arkivanov.decompose.ComponentContext
import letters.game.core.ComponentFactory
import org.koin.core.component.get
import org.koin.dsl.module
import letters.game.feautures.root.ui.RealRootComponent
import letters.game.feautures.root.ui.RootComponent

val rootModule = module {

}

fun ComponentFactory.createRootComponent(
    componentContext: ComponentContext,
    onCloseApp: () -> Unit
): RootComponent {
    return RealRootComponent(componentContext, get(), get(), onCloseApp)
}