package letters.game.core.utils

import kotlinx.coroutines.*

class Timer(private val block: (Int) -> Unit) {

    private var timerJob: Job? = null
    fun repeat(seconds: Int, coroutineScope: CoroutineScope) {
        stop()
        timerJob = coroutineScope.launch {
            repeat(seconds) {
                block(it)
                delay(1000)
            }
            block(seconds)
        }
    }

    fun doOnce(seconds: Int, coroutineScope: CoroutineScope) {
        stop()
        timerJob = coroutineScope.launch {
            delay(seconds.toLong())
            block(seconds)
        }
    }

    fun stop() {
        timerJob?.cancel()
    }
}