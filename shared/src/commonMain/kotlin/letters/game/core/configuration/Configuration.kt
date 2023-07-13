package letters.game.core.configuration

data class Configuration(
    val platform: Platform,
    val buildType: BuildType,
    val appVersionName: String,
    val appVersionCode: Int,
    val backend: Backend
)