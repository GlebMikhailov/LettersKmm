package letters.game.core.utils

inline fun <T> MutableList<T>.moveItemToTop(predicate: (T) -> Boolean) {
    withIndex().forEach {
        if (predicate(it.value)) {
            removeAt(it.index)
            add(0, it.value)
            return
        }
    }
}