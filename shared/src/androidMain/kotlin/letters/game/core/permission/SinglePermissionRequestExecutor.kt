package letters.game.core.permission

interface SinglePermissionRequestExecutor {
    suspend fun requestPermission(permission: String): Boolean
}