package letters.game.core.bottomsheet

import com.arkivanov.essenty.parcelable.Parcelable
import letters.game.core.utils.createFakeChildOverlay
import letters.game.core.state.CMutableStateFlow
import letters.game.core.state.CNullableMutableStateFlow
import letters.game.core.state.CNullableStateFlow
import letters.game.core.state.CStateFlow

class FakeBottomSheetControl<C : Parcelable, T : Any>(bottomSheetComponent: T) :
    BottomSheetControl<C, T>() {
    override val sheetOverlay = createFakeChildOverlay(bottomSheetComponent)

    override val halfExpandingSupported: Boolean = true
    override val hidingSupported: Boolean = true
    override val sheetState: CStateFlow<State> = CMutableStateFlow(State.Hidden)
    override val config: CNullableStateFlow<C> = CNullableMutableStateFlow(null)
    override val dismissedEvent: CStateFlow<Unit> = CMutableStateFlow(Unit)

    override fun shouldUpdateState(newState: State): Boolean = true
    override fun onStateChangedFromUI(state: State) = Unit
    override fun show(config: C) = Unit
    override fun dismiss() = Unit
}