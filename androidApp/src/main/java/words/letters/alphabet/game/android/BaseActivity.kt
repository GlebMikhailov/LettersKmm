package words.letters.alphabet.game.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.essenty.lifecycle.asEssentyLifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import letters.game.core.activityProvider
import letters.game.core.core
import letters.game.core.theme.AppTheme
import letters.game.features.root.RootUi
import letters.game.feautures.root.ui.RootComponent
import java.util.Timer
import java.util.TimerTask

abstract class BaseActivity : FragmentActivity() {

    companion object {
        private const val TRY_TO_QUIT_INTERVAL_MS = 2000L
    }

    private lateinit var rootComponent: RootComponent

    private var isTriedToQuit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val activityProvider = application.core.activityProvider
        activityProvider.attachActivity(this)
        lifecycle.asEssentyLifecycle().doOnDestroy {
            activityProvider.detachActivity()
        }

        rootComponent = application.core.createRootComponent(defaultComponentContext(), ::closeApp)
        setContent {
            AppTheme {
                RootUi(rootComponent)
            }
        }
    }


    private fun closeApp() {
        if (isTriedToQuit) {
            finish()
        } else {
            showFinishMessage()
        }
        startBackPressedTimer()
    }

    private fun showFinishMessage() {
        isTriedToQuit = true
        Toast.makeText(
            this,
            getString(R.string.quit_toast_msg),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun startBackPressedTimer() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                isTriedToQuit = false
            }
        }, TRY_TO_QUIT_INTERVAL_MS)
    }
}