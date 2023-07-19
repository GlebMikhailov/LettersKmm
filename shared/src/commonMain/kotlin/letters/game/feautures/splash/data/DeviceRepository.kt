package letters.game.feautures.splash.data

import letters.game.feautures.splash.domain.Device
import me.aartikov.replica.single.Replica

interface DeviceRepository {
    val deviceReplica: Replica<Device>
}