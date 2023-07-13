package letters.game.core.permission

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import letters.game.core.core

@Composable
fun BindPermissionController() {
    val core = (LocalContext.current.applicationContext as Application).core
    val permissionController = core.koin.get<PermissionsController>()
    BindEffect(permissionController)
}
