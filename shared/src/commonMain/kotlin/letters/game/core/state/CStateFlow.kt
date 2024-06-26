package letters.game.core.state

import kotlinx.coroutines.flow.StateFlow

/**
 * A wrapper for [StateFlow]. Required for Swift interop.
 */
open class CStateFlow<T : Any>(private val stateFlow: StateFlow<T>) : StateFlow<T> by stateFlow

fun <T : Any> StateFlow<T>.toCStateFlow() = CStateFlow(this)