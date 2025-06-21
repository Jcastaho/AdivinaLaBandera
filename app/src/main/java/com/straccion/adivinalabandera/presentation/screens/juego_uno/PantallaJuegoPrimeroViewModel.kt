package com.straccion.adivinalabandera.presentation.screens.juego_uno


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.adivinalabandera.domain.models.ContadorState
import com.straccion.adivinalabandera.domain.models.JuegoUiState
import com.straccion.adivinalabandera.domain.models.Personaje
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PantallaJuegoPrimeroViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val dificultad: String = checkNotNull(savedStateHandle["dificultad"])
    val dificultadJuego = dificultad.toInt()

    // Estado del juego principal
    private val _uiState = MutableStateFlow(JuegoUiState())
    val uiState: StateFlow<JuegoUiState> = _uiState.asStateFlow()

    // Estado del contador regresivo
    private val _contadorState = MutableStateFlow(ContadorState())
    val contadorState: StateFlow<ContadorState> = _contadorState.asStateFlow()

    // Lista de todos los personajes disponibles
    private val listaPersonajes = Personaje.Personajes.listaPersonajes

    // Lista de personajes disponibles para mostrar (sin repetir)
    private var personajesDisponibles = listaPersonajes.toMutableList()

    // Lista de personajes ya mostrados
    private val personajesMostrados = mutableListOf<Personaje>()


    //contador inicial para la animacion negativa
    private val _vidaAnterior = MutableStateFlow(0)
    val puntuacionAnterior: StateFlow<Int> = _vidaAnterior.asStateFlow()

    private val _mostrarTopBar = MutableStateFlow(false)
    val mostrarTopBar: StateFlow<Boolean> = _mostrarTopBar

    //Vidas del usere
    private val _vidas = MutableStateFlow(3) // Estado interno
    val vidas: StateFlow<Int> = _vidas // Exponer como StateFlow inmutable

    private val _verTienda = MutableStateFlow(false)
    val verTienda: StateFlow<Boolean> = _verTienda

    fun MostrarTienda() {
        _verTienda.value = true
    }
    fun OcultarTienda() {
        _verTienda.value = false
    }

    init {
        // Mezclamos la lista inicial para que el orden sea aleatorio
        personajesDisponibles.shuffle()
        if (dificultadJuego != 0) _vidas.value = 1
        iniciarContador()
    }

    /**
     * Inicia el contador regresivo antes de comenzar el juego
     */
    private fun iniciarContador() {
        viewModelScope.launch {
            // Cuenta regresiva: 3, 2, 1
            for (i in 3 downTo 1) {
                _contadorState.update { it.copy(numeroVisible = i) }
                delay(1000)
            }

            // Finaliza el contador y comienza el juego
            _contadorState.update {
                it.copy(
                    numeroVisible = 0,
                    contadorActivo = false
                )
            }

            // Inicia el juego después de que termine el contador
            iniciarJuego()
        }
    }

    /**
     * Inicia la secuencia del juego principal
     */
    private fun iniciarJuego() {
        viewModelScope.launch {
            // Mostrar texto de introducción por 4 segundos
            delay(4000)
            _uiState.update { it.copy(mostrarTexto = false) }
            delay(700)

            // Obtener primer personaje y mostrar imagen
            actualizarPersonajeYRespuestas()
            _uiState.update { it.copy(mostrarImagen = true) }
            delay(1600)

            // Ocultar imagen y mostrar pregunta
            _uiState.update {
                it.copy(
                    mostrarImagen = false,
                    mostrarPregunta = true
                )
            }
        }
    }

    /**
     * Obtiene un personaje no repetido y sus respuestas mezcladas
     */
    private fun obtenerPersonajeAleatorio(): Pair<Personaje, List<String>> {
        // Si ya no hay personajes disponibles, reiniciamos la lista
        if (personajesDisponibles.isEmpty()) {
            personajesDisponibles = personajesMostrados.toMutableList()
            personajesMostrados.clear()
            personajesDisponibles.shuffle()
        }

        // Tomamos el primer personaje de la lista disponible
        val personaje = personajesDisponibles.removeAt(0)
        personajesMostrados.add(personaje)

        val respuestaCorrecta = personaje.respuestasCorrectas

        // Creamos un conjunto de respuestas incorrectas que no incluyan la respuesta correcta
        val todasLasRespuestasIncorrectas = listaPersonajes
            .filter { it != personaje }
            .map { it.respuestasCorrectas }
            .distinct()
            .shuffled()
            .take(3)

        // Mezclamos todas las respuestas
        val todasLasRespuestas =
            (listOf(respuestaCorrecta) + todasLasRespuestasIncorrectas).shuffled()

        return Pair(personaje, todasLasRespuestas)
    }

    /**
     * Actualiza el personaje actual y las respuestas en el estado
     */
    private fun actualizarPersonajeYRespuestas() {
        val (nuevoPersonaje, nuevasRespuestas) = obtenerPersonajeAleatorio()
        _uiState.update {
            it.copy(
                personajeActual = nuevoPersonaje,
                respuestas = nuevasRespuestas
            )
        }
    }

    /**
     * Maneja la respuesta del usuario
     */
    fun responder(respuesta: String) {
        // Guardar la puntuación anterior antes de actualizarla
        _vidaAnterior.value = vidas.value
        val personajeActual = _uiState.value.personajeActual ?: return

        // Verificar si la respuesta es correcta
        val esCorrecta = respuesta == personajeActual.respuestasCorrectas

        // Actualizar puntuación si es correcta
        if (esCorrecta) {
            val turnosDoble = _uiState.value.turnosDoblePuntaje
            val puntosAGanar = if (turnosDoble > 0) 2 else 1
            _uiState.update {
                it.copy(
                    puntuacion = it.puntuacion + puntosAGanar,
                    turnosDoblePuntaje = if (turnosDoble > 0) turnosDoble - 1 else 0
                )
            }
        } else {
            reducirVida()
        }

        // Mostrar siguiente personaje
        mostrarSiguientePersonaje()
    }

    /**
     * Muestra el siguiente personaje en el juego
     */
    private fun mostrarSiguientePersonaje() {
        viewModelScope.launch {
            actualizarPersonajeYRespuestas()
            _uiState.update { it.copy(mostrarImagen = true, mostrarPregunta = false) }
            delay(1600)
            _uiState.update { it.copy(mostrarImagen = false, mostrarPregunta = true) }
        }
    }

    fun reducirVida() {
        val escudoActivo = _uiState.value.escudoActivo
        if (escudoActivo) {
            // Cancelamos la pérdida de vida y desactivamos el escudo
            _uiState.update { it.copy(escudoActivo = false) }
            return
        }
        if (_vidas.value > 0) {
            _vidas.value -= 1
        }
    }

    fun MostrarTopBar(){
        _mostrarTopBar.value = true
    }
    fun OcultarTopBar(){
        _mostrarTopBar.value = false
    }

    ///////////// PODERES ///////////////
    fun ActivarPoder(poderId: String) {
        when (poderId) {
            "ojo_de_halcon" -> {
                // Mostrar de nuevo la imagen por 1.5 segundos
                viewModelScope.launch {
                    _uiState.update { it.copy(mostrarImagen = true, mostrarPregunta = false) }
                    delay(2000)
                    _uiState.update { it.copy(mostrarImagen = false, mostrarPregunta = true) }
                }
            }
            "escudo" -> {
                // Lógica para no perder vida en el siguiente fallo
                _uiState.update { it.copy(escudoActivo = true) }
            }
            "puntaje_doble" -> {
                // Activar estado que duplica puntos por 5 turnos
                _uiState.update { it.copy(turnosDoblePuntaje = 5) }
            }
            "eliminar_opcion" -> {
                // Eliminar una respuesta incorrecta aleatoria
                EliminarUnaRespuestaIncorrecta()
            }
        }
    }
    private fun EliminarUnaRespuestaIncorrecta() {
        val respuestasActuales = _uiState.value.respuestas.toMutableList()
        val personajeCorrecto = _uiState.value.personajeActual?.respuestasCorrectas ?: return

        val incorrectas = respuestasActuales.filter { it != personajeCorrecto }

        if (incorrectas.isNotEmpty()) {
            val aEliminar = incorrectas.random()
            respuestasActuales.remove(aEliminar)

            _uiState.update { it.copy(respuestas = respuestasActuales) }
        }
    }
    fun TerminarJuego() {
        _vidas.value = 0
    }

}