package words.letters.alphabet.google

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import letters.game.core.debug_tools.AndroidDebugTools
import letters.game.core.error_handling.ServerException
import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.devtools.DevToolsSettings
import me.aartikov.replica.devtools.ReplicaDevTools
import me.nemiron.hyperion.networkemulation.NetworkEmulatorInterceptor
import okhttp3.Interceptor
import java.io.IOException

class RealAndroidDebugTools(private val context: Context) : AndroidDebugTools {

    companion object {
        private const val REPLICA_DEVTOOLS_PORT = 8081
    }

    private val networkEmulatorInterceptor = NetworkEmulatorInterceptor(
        context,
        failureExceptionProvider = { IOException(ServerException(cause = null)) }
    )

    private val chuckerCollector = ChuckerCollector(
        context = context,
        showNotification = false,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    private val chuckerInterceptor = ChuckerInterceptor
        .Builder(context)
        .collector(chuckerCollector)
        .build()

    override val interceptors: List<Interceptor> = listOf(
        networkEmulatorInterceptor,
        chuckerInterceptor
    )

    override fun launch(replicaClient: ReplicaClient) {
        val settings = DevToolsSettings(port = REPLICA_DEVTOOLS_PORT)
        ReplicaDevTools(replicaClient, context, settings).launch()
    }
}