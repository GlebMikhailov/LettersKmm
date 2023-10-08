package letters.game.core.configuration

import letters.game.feautures.splash.domain.Device

data class Configuration(
    val platform: Platform,
    val buildType: BuildType,
    val appVersionName: String,
    val appVersionCode: Int,
    val backend: Backend,
    val device: DeviceInfo,
    val ad: Ad
)

data class DeviceInfo(
    val store: Device.Store,
    val model: String,
    val deviceId: String
)