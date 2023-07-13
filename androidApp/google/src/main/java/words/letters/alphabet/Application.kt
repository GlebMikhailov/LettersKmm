package words.letters.alphabet

import letters.game.core.CoreProvider
import letters.game.core.debug_tools.AndroidDebugTools
import words.letters.alphabet.game.android.BaseApplication
import words.letters.alphabet.google.RealAndroidDebugTools

class Application : BaseApplication(), CoreProvider {

    override val versionCode: Int = BuildConfig.VERSION_CODE
    override val versionName: String = BuildConfig.VERSION_NAME
    override val debugTools: AndroidDebugTools by lazy { RealAndroidDebugTools(applicationContext) }
}