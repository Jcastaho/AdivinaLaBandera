package com.straccion.adivinalabandera

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.components.updateLocale
import com.straccion.adivinalabandera.presentation.navigation.graph.root.RootNavGraph
import com.straccion.adivinalabandera.ui.theme.AdivinaLaBanderaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.getValue


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    private val viewModelMusic by viewModels<ViewModelMusic>()
    private val viewModelGestionarUser by viewModels<ViewModelGestionarUser>()

    private var signInAttempted = false // Variable para controlar si ya se intentó iniciar sesión


    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val idioma = prefs.getString("idioma", "es") ?: "es"
        val context = newBase.updateLocale(idioma)
        super.attachBaseContext(context)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        try {
            viewModelGestionarUser.CheckExistingSignIn(this)

            //esto se comenta por que es lo que ahce que aparezca siempre de forma invasiva el login
            viewModelGestionarUser.userSignedIn.observe(this) { isSignedIn ->
                if (!isSignedIn && !signInAttempted) {
                    // Marcar que ya se intentó iniciar sesión
                    viewModelGestionarUser.SignIn(this)
                    signInAttempted = true
                }
            }
        }catch (e: Exception){
            FirebaseCrashlytics.getInstance().recordException(e)
        }
        // Observar mensajes de error
        viewModelGestionarUser.errorMessage.observe(this) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                FirebaseCrashlytics.getInstance().log("Error desde oncreate: $it")
                // También puedes registrar como excepción no fatal
                FirebaseCrashlytics.getInstance().recordException(Exception("Error de usuario: $it"))
            }
        }

        setContent {
            FixedFontSizeApp {
                val isMuted = viewModelMusic.isMute.collectAsState()
                CompositionLocalProvider(
                    LocalDensity provides Density(LocalDensity.current.density, fontScale = 1f)
                ) {
                    LaunchedEffect(Unit) {
                        if (isMuted.value) {
                            viewModelMusic.initializePlayer(R.raw.musica_menu)
                            delay(1000)
                            viewModelMusic.play()
                        }
                    }
                    val lifecycleOwner = LocalLifecycleOwner.current
                    DisposableEffect(lifecycleOwner.lifecycle) {
                        val observer = LifecycleEventObserver { _, event ->
                            when (event) {
                                Lifecycle.Event.ON_STOP -> viewModelMusic.pause()
                                Lifecycle.Event.ON_START -> viewModelMusic.play()
                                Lifecycle.Event.ON_DESTROY -> viewModelMusic.release()
                                else -> {}
                            }
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }
                    AdivinaLaBanderaTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) {
                            navHostController = rememberNavController()
                            RootNavGraph(navHostController, viewModelMusic, viewModelGestionarUser)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun FixedFontSizeApp(content: @Composable () -> Unit) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    CompositionLocalProvider(
        LocalDensity provides Density(density.density, fontScale = 1f) // Ignora el fontScale del sistema
    ) {
        content()
    }
}