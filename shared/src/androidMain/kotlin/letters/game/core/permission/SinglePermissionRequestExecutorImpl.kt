package letters.game.core.permission

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import letters.game.core.activity.ActivityProvider

class SinglePermissionRequestExecutorImpl(
    private val activityProvider: ActivityProvider
) : SinglePermissionRequestExecutor {
    private var activityResultLauncher = MutableStateFlow<ActivityResultLauncher<String>?>(null)
    private val permissionsResultFlow = MutableSharedFlow<Boolean?>()

    init {
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch {
            activityProvider.activityStateFlow.collect {
                if (it != null) {
                    registerLauncher(it)
                } else {
                    unregisterLauncher()
                }
            }
        }
    }

    override suspend fun requestPermission(permission: String): Boolean {
        activityResultLauncher.filterNotNull().first().launch(permission)
        return permissionsResultFlow.firstOrNull() ?: false
    }

    private fun registerLauncher(activity: ComponentActivity) {
        activityResultLauncher.value =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                activity.lifecycleScope.launch {
                    Logger.v("requestPermission: registerLauncher; $it")
                    permissionsResultFlow.emit(it)
                }
            }
    }

    private suspend fun unregisterLauncher() {
        activityResultLauncher.value?.unregister()
        activityResultLauncher.value = null
        permissionsResultFlow.emit(null)
    }
}