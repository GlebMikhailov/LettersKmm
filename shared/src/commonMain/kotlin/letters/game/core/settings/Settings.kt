package letters.game.core.settings

interface Settings {

    suspend fun getString(key: String): String?

    suspend fun putString(key: String, value: String)

    suspend fun remove(key: String)
}