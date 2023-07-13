package letters.game.feautures.root.ui

import letters.game.feautures.home.ui.FakeHomeComponent
import letters.game.core.message.ui.FakeMessageComponent
import letters.game.core.utils.createFakeChildStack

class FakeRootComponent : RootComponent {


    override val childStack =
        createFakeChildStack(
            RootComponent.Child.Home(
                FakeHomeComponent()
            ) as RootComponent.Child
        )

    override val messageComponent = FakeMessageComponent()
}