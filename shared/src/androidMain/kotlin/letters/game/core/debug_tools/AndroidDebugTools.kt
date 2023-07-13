package letters.game.core.debug_tools

import me.aartikov.replica.client.ReplicaClient
import okhttp3.Interceptor

interface AndroidDebugTools {

    val interceptors: List<Interceptor>

    fun launch(replicaClient: ReplicaClient)
}