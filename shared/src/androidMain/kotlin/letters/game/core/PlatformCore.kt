package letters.game.core

import android.app.Application
import letters.game.core.activity.ActivityProvider
import letters.game.core.debug_tools.AndroidDebugTools
import me.aartikov.replica.client.ReplicaClient

val Application.core get() = (this as CoreProvider).core

val Core.activityProvider get() = koin.get<ActivityProvider>()

fun Core.launchAndroidDebugTools() {
    val replicaClient = koin.get<ReplicaClient>()
    val androidDebugTools = koin.get<AndroidDebugTools>()
    androidDebugTools.launch(replicaClient)
}