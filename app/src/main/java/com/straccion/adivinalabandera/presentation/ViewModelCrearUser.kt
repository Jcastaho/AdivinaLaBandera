package com.straccion.adivinalabandera.presentation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.PlayGamesSdk
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.straccion.adivinalabandera.domain.models.DatosUser
import com.straccion.adivinalabandera.domain.models.Response
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ObtenerDatosUseCases
import com.straccion.adivinalabandera.presentation.components.updateLocale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelGestionarUser @Inject constructor(
    private val guardar: ObtenerDatosUseCases,
    private val application: Application
) : ViewModel() {


    //region guardar con sharedpreference el rechazo de sesion de google games Revisar mas adelante
//    private val prefs = application.getSharedPreferences("settings", Context.MODE_PRIVATE)
//
//    fun setUserDeclinedLogin(declined: Boolean) {
//        prefs.edit().putBoolean("user_declined_login", declined).apply()
//    }
//
//    fun hasUserDeclinedLogin(): Boolean {
//        return prefs.getBoolean("user_declined_login", false)
//    }

    //     Y en SigIn colocar al inicio este return:
//    if (hasUserDeclinedLogin()) {
//        return // No mostrar diálogo si el usuario ya lo rechazó
//    }
    //colocar un boton para que inicie sesion

    //endregion

    private val TAG = "ViewModelGestionarUser"
    var playerId = ""

    private var _gemas: MutableStateFlow<Int> = MutableStateFlow(0)
    var gemas: StateFlow<Int> = _gemas.asStateFlow()

    ////////
    private val _poderesJuego1 = MutableStateFlow<Map<String, Int>>(emptyMap())
    val poderesJuego1: StateFlow<Map<String, Int>> = _poderesJuego1

    private val _poderesJuego2 = MutableStateFlow<Map<String, Int>>(emptyMap())
    val poderesJuego2: StateFlow<Map<String, Int>> = _poderesJuego2
    ////////


    private val _userSignedIn = MutableLiveData<Boolean>()
    val userSignedIn: LiveData<Boolean> = _userSignedIn

    private val _userProfile = mutableStateOf<DatosUser?>(null)
    val userProfile: State<DatosUser?> = _userProfile

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    //habilita para cambiar el nombre
    private val _isEditable = MutableStateFlow(false)
    val isEditable: StateFlow<Boolean> = _isEditable

    //nuevo nombre
    private val _nuevoNombre = MutableStateFlow("")
    val nuevoNombre: StateFlow<String> = _nuevoNombre


    init {
        // Inicializar Play Games SDK
        PlayGamesSdk.initialize(application)
    }

    /** Intenta iniciar sesión con Google Play Games
     * Esta función debe ser llamada explícitamente por un botón o acción del usuario
     */
    fun SignIn(activity: Activity) {
        val playGamesSignInClient = PlayGames.getGamesSignInClient(activity)

        // Verificar si el usuario está autenticado
        playGamesSignInClient.isAuthenticated().addOnCompleteListener { signInTask  ->
            if (signInTask.isSuccessful) {
                // Agregar un pequeño retraso (ejemplo: 500 milisegundos)
                Handler(Looper.getMainLooper()).postDelayed({
                    // Verificar nuevamente si está autenticado antes de buscar los datos
                    playGamesSignInClient.isAuthenticated().addOnCompleteListener { isAuthenticatedTask ->
                        if (isAuthenticatedTask.isSuccessful && isAuthenticatedTask.result.isAuthenticated) {
                            FetchUserData(activity)
                        } else {
                            _errorMessage.value = "Error al verificar la sesión después del inicio de sesión."
                            Log.w(TAG, "Error al verificar la sesión", isAuthenticatedTask.exception)
                            FirebaseCrashlytics.getInstance().recordException(isAuthenticatedTask.exception ?: Exception("Error al verificar la sesión después del inicio de sesión"))
                            _userSignedIn.value = false
                        }
                    }
                }, 500)
            } else {
                // Error en el inicio de sesión
                _errorMessage.value =
                    "Error al iniciar sesión: ${signInTask.exception?.message}"
                _userSignedIn.value = false
                Log.w(TAG, "signInResult:failed", signInTask.exception)
                FirebaseCrashlytics.getInstance().recordException(signInTask.exception ?: Exception("Error desconocido en signIn viewmodel"))
            }
        }
    }

    /**
     * Obtiene los datos del usuario autenticado
     */
    private fun FetchUserData(activity: Activity) {
        val playersClient = PlayGames.getPlayersClient(activity)

        // Obtener información del jugador actual
        playersClient.getCurrentPlayer().addOnCompleteListener { playerTask ->
            if (playerTask.isSuccessful) {
                val player = playerTask.result

                // Obtener datos del jugador
                playerId = player.playerId
                val displayName = player.displayName
                val iconImageUri = player.iconImageUri?.toString()
                val iconImageUr = player.iconImageUri
                val hiResImageUri = player.hiResImageUri

                Log.d(TAG, "Player ID: $playerId")
                Log.d(TAG, "Display Name: $displayName")
                Log.d(TAG, "Icon Image: $iconImageUri")
                Log.d(TAG, "hiResImageUri: $hiResImageUri")
                Log.d(TAG, "iconImageUr: $iconImageUr")

                if (playerId != null && displayName != null) {
                    // Crear perfil de usuario
                    val profile = DatosUser(
                        idUser = playerId,
                        nombre = displayName,
                        avatar = iconImageUri.toString()
                    )

                    // Actualizar LiveData
                    _userProfile.value = profile
                    _userSignedIn.value = true

                    // Guardar en Firebase
                    SaveUserToFirebase(profile)
                } else {
                    _errorMessage.value = "No se pudieron obtener todos los datos del perfil"
                    Log.d(TAG, "Datos incompletos: ID=$playerId, Nombre=$displayName")
                }
            } else {
                _errorMessage.value =
                    "Error al obtener datos del perfil: ${playerTask.exception?.message}"
                _userSignedIn.value = false
                Log.d(TAG, "getCurrentPlayer:failed", playerTask.exception)
                FirebaseCrashlytics.getInstance().recordException(playerTask.exception ?: Exception("Error desconocido al obtener FetchUserData viewmodel"))
            }
        }
    }

    private fun SaveUserToFirebase(datosUser: DatosUser) {
        viewModelScope.launch {
            val guardarResponse = guardar.guardarNuevoUser(datosUser)
            if (guardarResponse is Response.Success) {
                Log.d("PlayGames", "Usuario registrado correctamente")
            } else if (guardarResponse is Response.Failure) {
                Log.d("PlayGames", "Error al registrar usuario: ${guardarResponse.exception}")
            }
            guardar.obtenerGemasUser(datosUser.idUser)
                .collect { response ->
                    when (response) {
                        is Response.Success -> {
                            _gemas.value = response.data
                        }
                        is Response.Failure -> {
                            // Manejar el error, quizás mostrar un mensaje
                            Log.e("GemasError", "Error obteniendo gemas", response.exception)
                        }
                        else -> ""
                    }
                }
        }
    }

    /**
     * Verifica si ya hay una sesión activa
     */
    fun CheckExistingSignIn(activity: Activity) {
        val playGamesSignInClient = PlayGames.getGamesSignInClient(activity)

        playGamesSignInClient.isAuthenticated().addOnCompleteListener { authTask ->
            if (authTask.isSuccessful && authTask.result.isAuthenticated) {
                // Ya hay sesión, obtener datos del usuario
                FetchUserData(activity)
            } else {
                _userSignedIn.value = false
            }
        }
    }

    private val _listaUsuarios = MutableStateFlow<Response<List<DatosUser>>>(Response.Loading)
    val listaUsuarios: StateFlow<Response<List<DatosUser>>> = _listaUsuarios.asStateFlow()

    private val _usuario = MutableStateFlow<Response<DatosUser?>>(Response.Loading)


    fun ActualizarScore(nuevoScore: Int, juego: String, dificultadJuego: Int) {
        viewModelScope.launch {
            try {
                guardar.obtenerDatosUserPorId(playerId).collect { response ->
                    _usuario.value = response

                    if (response is Response.Success) {
                        val datosUser = response.data
                        datosUser?.let { usuario ->
                            val tipoJuegos = usuario.tipoJuegos
                            val juegoJugado = tipoJuegos[juego] as? Map<String, Any>
                            val claveScore = if (dificultadJuego == 0) "normal" else "dificil"
                            val scoreActual = (juegoJugado?.get(claveScore) as? Long)?.toInt()

                            val debeActualizar =
                                (scoreActual != null && nuevoScore > scoreActual) ||
                                        (juegoJugado == null && scoreActual == null)

                            if (debeActualizar) {
                                guardar.actualizarScoresUser(
                                    idUser = playerId,
                                    score = nuevoScore,
                                    dificultad = dificultadJuego,
                                    nombreJuego = juego
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _usuario.value = Response.Failure(e)
                Log.d(TAG, "Error: ${e.message}")
            }
        }
    }

    fun ObtenerRankingCompleto(juego: String, dificultad: Int) = viewModelScope.launch {
        _listaUsuarios.value = Response.Loading
        try {
            guardar.allDatosUser().collect { response ->
                when (response) {
                    is Response.Success -> {
                        val usuarios = response.data.sortedByDescending { user ->
                            val tipoJuegos = user.tipoJuegos
                            val juegoJugado = tipoJuegos[juego] as? Map<String, Any>
                            val claveScore = if (dificultad == 0) "normal" else "dificil"
                            (juegoJugado?.get(claveScore) as? Long)?.toInt() ?: 0
                        }

                        _listaUsuarios.value = Response.Success(usuarios)
                    }

                    is Response.Failure -> _listaUsuarios.value = response
                    else -> _listaUsuarios.value = Response.Failure(Exception("Estado inesperado"))
                }
            }
        } catch (e: Exception) {
            _listaUsuarios.value = Response.Failure(e)
        }
    }

    fun ObtenerTop20Usuarios(
        listaUsuarios: List<DatosUser>,
        juego: String,
        dificultad: Int
    ): List<DatosUser> {
        return listaUsuarios.sortedByDescending { user ->
            val tipoJuegos = user.tipoJuegos
            val juegoJugado = tipoJuegos[juego] as? Map<String, Any>
            val claveScore = if (dificultad == 0) "normal" else "dificil"
            (juegoJugado?.get(claveScore) as? Long)?.toInt() ?: 0
        }.take(20)
    }

    fun ObtenerPuestoUsuario(
        listaUsuarios: List<DatosUser>,
        playerId: String,
        juego: String,
        dificultad: Int
    ): Int? {
        val usuariosOrdenados = listaUsuarios.sortedByDescending { user ->
            val tipoJuegos = user.tipoJuegos
            val juegoJugado = tipoJuegos[juego] as? Map<String, Any>
            val claveScore = if (dificultad == 0) "normal" else "dificil"
            (juegoJugado?.get(claveScore) as? Long)?.toInt() ?: 0
        }

        return usuariosOrdenados.indexOfFirst { it.idUser == playerId }
            .takeIf { it != -1 }
            ?.plus(1) // Ranking comienza en 1
    }
    //Config
    fun MostarEditarNombre() {
        _isEditable.value = true
    }

    fun OcultarEditarNombre() {
        _isEditable.value = false
    }

    fun NuevoNombre(nombre: String) {
        viewModelScope.launch {
            _nuevoNombre.value = nombre
        }
    }

    fun CambiarIdioma(context: Context, idioma: String) {
        // Guardar idioma en SharedPreferences
        val sharedPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().putString("idioma", idioma).apply()

        // Aplicar nuevo idioma
        context.updateLocale(idioma)

        // Reiniciar la actividad para aplicar el cambio
        (context as? Activity)?.recreate()
    }

    fun PoderesActualizar(gemasAdicional: Int, sumar: Boolean, numeroJuego: String,  poder: String, estaComprando: Boolean){
        viewModelScope.launch {
            if (estaComprando){
                guardar.actualizarPoderes(playerId, numeroJuego, poder, 1)
                ActualizarGemas(gemasAdicional, sumar)
            }else{
                guardar.actualizarPoderes(playerId, numeroJuego, poder, -1)
            }
        }
    }

    fun ActualizarGemas(gemasAdicional: Int, sumar: Boolean){
        viewModelScope.launch {
            var gemitas = 0
            gemitas = if (sumar) {
                gemas.value + gemasAdicional
            } else {
                gemas.value - gemasAdicional
            }
            guardar.actualizarGemasUser(playerId, gemitas)
        }
    }

    fun CargarPoderesJuego1() {
        viewModelScope.launch {
            guardar.obtenerPoderesJuego1(playerId).collect { response ->
                when (response) {
                    is Response.Success -> _poderesJuego1.value = response.data
                    is Response.Failure -> Log.e("ErrorPoderes", "Error cargando poderes juego1", response.exception)
                    else -> {}
                }
            }
        }
    }
    fun CargarPoderesJuego2() {
        viewModelScope.launch {
            guardar.obtenerPoderesJuego2(playerId).collect { response ->
                when (response) {
                    is Response.Success -> _poderesJuego2.value = response.data
                    is Response.Failure -> Log.e("ErrorPoderes", "Error cargando poderes juego2", response.exception)
                    else -> {}
                }
            }
        }
    }
}