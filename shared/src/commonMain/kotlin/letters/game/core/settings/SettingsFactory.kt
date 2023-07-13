package letters.game.core.settings

interface SettingsFactory {

    fun createSettings(name: String): Settings

    fun createEncryptedSettings(): Settings // multiple encrypted settings are not supported due to iOS limitations.
}