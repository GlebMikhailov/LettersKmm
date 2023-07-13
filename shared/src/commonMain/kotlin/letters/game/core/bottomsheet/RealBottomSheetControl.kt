package letters.game.core.bottomsheet

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.overlay.ChildOverlay
import com.arkivanov.decompose.router.overlay.OverlayNavigation
import com.arkivanov.decompose.router.overlay.activate
import com.arkivanov.decompose.router.overlay.childOverlay
import com.arkivanov.decompose.router.overlay.dismiss
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import letters.game.core.utils.componentScope
import letters.game.core.utils.toCStateFlow
import ru.flawery.core.state.CMutableStateFlow
import ru.flawery.core.state.CNullableMutableStateFlow
import ru.flawery.core.state.CStateFlow
import kotlin.reflect.KClass

private const val SHEET_CHILD_OVERLAY_KEY = "sheetChildOverlay"

inline fun <reified C : Parcelable, T : Any> ComponentContext.bottomSheetControl(
    noinline bottomSheetComponentFactory: (C, ComponentContext, BottomSheetControl<C, T>) -> T,
    key: String? = null,
    hidingSupported: Boolean = true,
    halfExpandingSupported: Boolean = false,
    persistent: Boolean = true
): BottomSheetControl<C, T> {
    return bottomSheetControl(
        bottomSheetComponentFactory,
        key,
        hidingSupported = hidingSupported,
        halfExpandingSupported = halfExpandingSupported,
        persistent = false,
        C::class,
    )
}

fun <C : Parcelable, T : Any> ComponentContext.bottomSheetControl(
    bottomSheetComponentFactory: (C, ComponentContext, BottomSheetControl<C, T>) -> T,
    key: String? = null,
    hidingSupported: Boolean,
    halfExpandingSupported: Boolean,
    persistent: Boolean,
    clazz: KClass<C>,
): BottomSheetControl<C, T> = RealBottomSheetControl(
    this,
    bottomSheetComponentFactory,
    key ?: SHEET_CHILD_OVERLAY_KEY,
    hidingSupported = hidingSupported,
    halfExpandingSupported = halfExpandingSupported,
    persistent = persistent,
    clazz,
)

private class RealBottomSheetControl<C : Parcelable, T : Any>(
    componentContext: ComponentContext,
    private val bottomSheetComponentFactory: (C, ComponentContext, BottomSheetControl<C, T>) -> T,
    key: String,
    override val hidingSupported: Boolean,
    override val halfExpandingSupported: Boolean,
    persistent: Boolean,
    clazz: KClass<C>,
) : BottomSheetControl<C, T>() {

    private val logger = Logger.withTag("BottomSheetControl")

    private val sheetNavigation = OverlayNavigation<C>()

    override val sheetOverlay: CStateFlow<ChildOverlay<*, T>> =
        componentContext.childOverlay(
            source = sheetNavigation,
            handleBackButton = hidingSupported,
            key = key,
            configurationClass = clazz,
            childFactory = { configuration, context ->
                bottomSheetComponentFactory(configuration, context, this)
            },
            persistent = persistent
        ).toCStateFlow(componentContext.lifecycle)

    override val sheetState = CMutableStateFlow(State.Hidden)

    override val config = CNullableMutableStateFlow<C>(null)

    override val dismissedEvent = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        sheetOverlay
            .onEach {
                if (it.overlay == null) {
                    config.value = null
                    sheetState.value = State.Hidden
                }
            }
            .launchIn(componentContext.componentScope)
    }

    override fun onStateChangedFromUI(state: State) {
        if (!shouldUpdateState(state)) return

        if (sheetOverlay.value.overlay == null) {
            logger.w("BottomSheetControl: overlay is null")
            return
        }

        if (state == State.Hidden) {
            dismiss()
        } else {
            sheetState.value = state
        }
    }

    override fun show(config: C) {
        sheetNavigation.activate(config)
        this.config.value = config
        sheetState.value = State.Expanded
    }

    override fun dismiss() {
        sheetNavigation.dismiss()
    }

    override fun shouldUpdateState(newState: State) = when (newState) {
        State.Expanded -> true
        State.HalfExpanded -> halfExpandingSupported
        State.Hidden -> hidingSupported
    }
}