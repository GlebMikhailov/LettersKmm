package letters.game.feautures.splash.data

import letters.game.core.configuration.Configuration
import letters.game.core.configuration.toRemote
import letters.game.core.network.ReplicaTags
import letters.game.feautures.splash.data.dto.DeviceRequest
import letters.game.feautures.splash.data.dto.toDevice
import letters.game.feautures.splash.domain.Device
import letters.game.feautures.splash.domain.toRemote
import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.single.Replica
import me.aartikov.replica.single.ReplicaSettings
import kotlin.time.Duration.Companion.minutes

class DeviceRepositoryImpl(
    replicaClient: ReplicaClient,
    private val deviceApi: DeviceApi,
    configuration: Configuration
) : DeviceRepository {

    override val deviceReplica: Replica<Device> = replicaClient.createReplica(
        name = "device",
        settings = ReplicaSettings(staleTime = 5.minutes),
        tags = setOf(ReplicaTags.Game),
        fetcher = {
            val deviceInfo = configuration.device
            val request = DeviceRequest(
                deviceId = deviceInfo.deviceId,
                model = deviceInfo.model,
                type = configuration.platform.type.toRemote(),
                store = deviceInfo.store.toRemote()
            )
            deviceApi.createDevice(request).data.toDevice()
        }
    )
}