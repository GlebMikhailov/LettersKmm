package letters.game.feautures.splash.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import letters.game.feautures.game.domain.Game
import letters.game.feautures.splash.data.dto.DeviceRequest

@Parcelize
data class DeviceId(
    val value: Long
) : Parcelable

@Parcelize
data class Device(
    val id: DeviceId,
    val deviceId: String,
    val model: String,
    val type: DeviceType,
    val store: Store,
    val ip: String,
    val freeGames: Int,
    val lastGame: Game?
) : Parcelable {
    companion object {
        val DEFAULT = Device(
            id = DeviceId(-1),
            deviceId = "",
            model = "",
            type = DeviceType.Ios,
            store = Store.AppStore,
            ip = "",
            freeGames = 0,
            lastGame = null
        )

        val MOCK = Device(
            id = DeviceId(134),
            deviceId = "wdjpifh0efg2f2983rgg",
            model = "iPhone 13 pro max",
            type = DeviceType.Ios,
            store = Store.AppStore,
            ip = "223.231.44.21",
            freeGames = 7,
            lastGame = null
        )
    }

    enum class DeviceType {
        Ios, Android
    }

    enum class Store {
        GooglePlay, AppStore, AppGallery, None
    }
}

fun Device.DeviceType.toRemote(): String {
    return when (this) {
        Device.DeviceType.Ios -> "ios"
        Device.DeviceType.Android -> "android"
    }
}

fun Device.Store.toRemote(): String {
    return when (this) {
        Device.Store.GooglePlay -> "google_play"
        Device.Store.AppStore -> "app_store"
        Device.Store.AppGallery -> "app_gallery"
        Device.Store.None -> "none"
    }
}


fun Device.toRequest() = DeviceRequest(
    deviceId = deviceId,
    model = model,
    type = type.toRemote(),
    store = store.toRemote()
)