package letters.game.core.utils

import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.aartikov.replica.decompose.observe
import me.aartikov.replica.single.Loadable
import me.aartikov.replica.single.Replica
import me.aartikov.replica.single.currentState
import letters.game.core.error_handling.ErrorHandler
import letters.game.core.error_handling.errorMessage
import letters.game.core.state.CStateFlow
import letters.game.core.state.computed

/**
 * An analogue of [Loadable] but with localized error message. Required for Swift interop.
 */
data class LoadableState<T : Any>(
    val loading: Boolean = false,
    val data: T? = null,
    val error: StringDesc? = null
)

/**
 * Observes [Replica] and handles errors by [ErrorHandler].
 */
fun <T : Any> Replica<T>.observe(
    componentContext: ComponentContext,
    errorHandler: ErrorHandler
): CStateFlow<LoadableState<T>> {

    val observer = observe(componentContext.lifecycle)

    observer
        .loadingErrorFlow
        .onEach { error ->
            errorHandler.handleError(
                error.exception,
                showError = observer.currentState.data != null // show error only if fullscreen error is not shown
            )
        }
        .launchIn(componentContext.componentScope)

    return componentContext.computed(observer.stateFlow) {
        LoadableState(it.loading, it.data, it.error?.exception?.errorMessage)
    }
}

fun <T : Any, R : Any> CStateFlow<LoadableState<T>>.mapData(
    componentContext: ComponentContext,
    transform: (T) -> R?
): CStateFlow<LoadableState<R>> {
    return componentContext.computed(this) { value ->
        value.mapData { transform(it) }
    }
}

fun <T : Any, R : Any> LoadableState<T>.mapData(
    transform: (T) -> R?
): LoadableState<R> {
    return LoadableState(loading, data?.let { transform(it) }, error)
}
