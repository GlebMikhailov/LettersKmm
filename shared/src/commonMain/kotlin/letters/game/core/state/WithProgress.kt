package letters.game.core.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KMutableProperty0

suspend fun withProgress(
    progressProperty: KMutableProperty0<Boolean>,
    block: suspend () -> Unit
) {
    try {
        progressProperty.set(true)
        block()
    } finally {
        progressProperty.set(false)
    }
}

suspend fun withProgress(
    progressStateFlow: MutableStateFlow<Boolean>,
    block: suspend () -> Unit
) {
    try {
        progressStateFlow.value = true
        block()
    } finally {
        progressStateFlow.value = false
    }
}