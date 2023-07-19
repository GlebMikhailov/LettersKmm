package letters.game.core.configuration


enum class PlatformType {
    Android,
    Ios
}

expect class Platform {
    val type: PlatformType
}

fun PlatformType.toRemote(): String {
    return when (this) {
        PlatformType.Ios -> "ios"
        PlatformType.Android -> "android"
    }
}