package letters.game.core.dialog

import com.arkivanov.decompose.router.overlay.ChildOverlay
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.coroutines.flow.Flow
import letters.game.core.state.CStateFlow

abstract class DialogControl<C : Parcelable, T : Any> {
    abstract val dialogOverlay: CStateFlow<ChildOverlay<*, T>>
    abstract val dismissEvent: Flow<Unit>
    abstract val canDismissed: Boolean

    abstract fun show(config: C)
    abstract fun dismiss()
}