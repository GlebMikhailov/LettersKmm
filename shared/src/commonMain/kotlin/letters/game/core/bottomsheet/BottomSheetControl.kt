package letters.game.core.bottomsheet

import com.arkivanov.decompose.router.overlay.ChildOverlay
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.coroutines.flow.Flow
import letters.game.core.state.CNullableStateFlow
import letters.game.core.state.CStateFlow

/**
 * Class to configure and control Bottom Sheet's behaviours
 */
abstract class BottomSheetControl<C : Parcelable, T : Any> {
    abstract val sheetOverlay: CStateFlow<ChildOverlay<*, T>>
    abstract val sheetState: CStateFlow<State>
    abstract val config: CNullableStateFlow<C>
    abstract val hidingSupported: Boolean
    abstract val halfExpandingSupported: Boolean
    abstract val dismissedEvent: Flow<Unit>

    abstract fun shouldUpdateState(newState: State): Boolean
    abstract fun onStateChangedFromUI(state: State)
    abstract fun show(config: C)
    abstract fun dismiss()

    enum class State {
        Expanded, HalfExpanded, Hidden
    }
}