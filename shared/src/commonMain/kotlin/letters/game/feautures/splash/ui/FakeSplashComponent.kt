package letters.game.feautures.splash.ui

import letters.game.core.state.CMutableStateFlow
import letters.game.core.state.CStateFlow
import letters.game.core.utils.LoadableState
import letters.game.feautures.splash.domain.Device

object FakeSplashComponent : SplashComponent {

    override val deviceState: CStateFlow<LoadableState<Device>> =
        CMutableStateFlow(LoadableState(data = Device.DEFAULT))
}