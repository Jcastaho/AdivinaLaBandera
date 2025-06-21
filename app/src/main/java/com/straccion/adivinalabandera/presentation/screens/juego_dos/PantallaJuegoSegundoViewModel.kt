package com.straccion.adivinalabandera.presentation.screens.juego_dos


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.adivinalabandera.domain.models.ContadorState
import com.straccion.adivinalabandera.domain.models.JuegoDosUiState
import com.straccion.adivinalabandera.domain.models.LetraSeleccionada
import com.straccion.adivinalabandera.domain.models.Personaje
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PantallaJuegoSegundoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val dificultad: String = checkNotNull(savedStateHandle["dificultad"])
    val dificultadJuego = dificultad.toInt()

    private val _tiempoRestante = MutableStateFlow(60) // 60 segundos
    val tiempoRestante: StateFlow<Int> = _tiempoRestante

    private var temporizadorJob: Job? = null

    // Estado del juego principal

    // Estado del contador regresivo
    private val _contadorState = MutableStateFlow(ContadorState())
    val contadorState: StateFlow<ContadorState> = _contadorState.asStateFlow()

    // Lista de todos los personajes disponibles
    private val listaPersonajes = Personaje.Personajes.listaPersonajes

    // Lista de personajes disponibles para mostrar (sin repetir)
    private var personajesDisponibles = listaPersonajes.toMutableList()

    // Lista de personajes ya mostrados
    private val personajesMostrados = mutableListOf<Personaje>()

    private val _uiStateLetras = MutableStateFlow(JuegoDosUiState())
    val uiStateLetras: StateFlow<JuegoDosUiState> = _uiStateLetras.asStateFlow()

    //region Estados de tienda y topbar
    private val _verTienda = MutableStateFlow(false)
    val verTienda: StateFlow<Boolean> = _verTienda
    private val _mostrarTopBar = MutableStateFlow(false)
    val mostrarTopBar: StateFlow<Boolean> = _mostrarTopBar
    //endregion

    init {
        personajesDisponibles.shuffle()
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
            // Mostrar texto de introducción por 5 segundos
            delay(5500)
            _uiStateLetras.update { it.copy(mostrarTexto = false) }
            delay(700)

            // Obtener primer personaje y mostrar imagen
            actualizarPersonajeYRespuestas()
            _uiStateLetras.update { it.copy(mostrarImagen = true) }
        }
    }

    fun iniciarTemporizador() {
        temporizadorJob?.cancel()
        if (dificultadJuego == 0) {
            _tiempoRestante.value = 80
        } else {
            _tiempoRestante.value = 60
        }

        // Total de 12 áreas (0-11)
        val todasLasAreas = (0..11).toList().shuffled()

        // Iniciar con un área descubierta (segundo 60)
        _uiStateLetras.update { it.copy(areasVisibles = listOf(todasLasAreas[0])) }

        // Lista de áreas restantes (11 elementos, del índice 1 al 11)
        val areasRestantes = todasLasAreas.drop(1)

        temporizadorJob = viewModelScope.launch {
            var areasMostradas = 1 // Ya mostramos la primera (índice 0)

            while (_tiempoRestante.value > 0) {
                if (_verTienda.value || _uiStateLetras.value.tiempoCongelado) {
                    delay(100L) // Pausa si está en tienda o tiempo congelado
                    continue
                }

                delay(1000L)
                _tiempoRestante.value -= 1

                if ((60 - _tiempoRestante.value) % 5 == 0 && areasMostradas < 12) {
                    agregarAreaVisible(areasRestantes[areasMostradas - 1])
                    areasMostradas++
                }
            }
        }
    }

    /**
     * Agrega un área específica a las áreas visibles
     */
    private fun agregarAreaVisible(area: Int) {
        val actuales = _uiStateLetras.value.areasVisibles.toMutableList()
        actuales.add(area)
        _uiStateLetras.update { it.copy(areasVisibles = actuales) }
    }

    override fun onCleared() {
        super.onCleared()
        temporizadorJob?.cancel()
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

        iniciarTemporizador()
        return Pair(personaje, todasLasRespuestas)
    }

    fun verificarRespuesta() {
        val estado = _uiStateLetras.value
        val nombreSinEspacios = estado.nombreCorrecto.filter { it != ' ' }.uppercase()

        // Crear un set para almacenar los índices de las letras incorrectas
        val incorrectas = mutableSetOf<Int>()

        // Comparar cada letra seleccionada con la letra correspondiente en el nombre correcto
        for (i in estado.letrasSeleccionadas.indices) {
            val letraSeleccionada = estado.letrasSeleccionadas[i].letra.uppercaseChar()
            val letraCorrecta = nombreSinEspacios.getOrNull(i)?.uppercaseChar()

            if (letraSeleccionada != letraCorrecta) {
                incorrectas.add(i)
            }
        }

        // Actualizar el estado con las letras incorrectas
        _uiStateLetras.value = estado.copy(letrasIncorrectas = incorrectas)

        // Si no hay letras incorrectas, la respuesta es correcta
        val esCorrecta = incorrectas.isEmpty()

        // Aquí puedes agregar lógica adicional para manejar respuestas correctas
        if (esCorrecta) {
            if (_uiStateLetras.value.tiempoCongelado){
                _uiStateLetras.update { it.copy(tiempoCongelado = false) }
            }
            val turnosDoble = _uiStateLetras.value.puntajeDobleActivado
            val puntosAGanar =
                if (turnosDoble > 0) _tiempoRestante.value * 2 else _tiempoRestante.value
            _uiStateLetras.update {
                it.copy(
                    puntuacion = it.puntuacion + puntosAGanar,
                    puntajeDobleActivado = if (turnosDoble > 0) turnosDoble - 1 else 0
                )
            }
            actualizarPersonajeYRespuestas()
        }
    }

    private fun actualizarPersonajeYRespuestas() {
        val (nuevoPersonaje, nuevasRespuestas) = obtenerPersonajeAleatorio()
        _uiStateLetras.update {
            it.copy(
                personajeActual = nuevoPersonaje,
                respuestas = nuevasRespuestas,
                letrasReveladas = emptySet() // <- Esto limpia las letras reveladas
            )
        }

        _uiStateLetras.value = _uiStateLetras.value.copy(
            nombreCorrecto = nuevoPersonaje.respuestasCorrectas,
            letrasSeleccionadas = emptyList(), // <- Aquí vacías las letras viejas
            poolLetras = generarPoolLetras(nuevoPersonaje.respuestasCorrectas),

            )
    }

    //region Letras mostradas
    private fun generarPoolLetras(nombre: String): List<Char> {
        val nombreMayus = nombre.uppercase()
        var masLetras = 0

        // 1. Letras necesarias exactas (excluye espacios)
        val letrasNombre = nombreMayus.filter { it != ' ' }.toList()
        masLetras = if (letrasNombre.size > 12) {
            3
        } else {
            6
        }

        // 2. Letras extras aleatorias (que no estén ya en el nombre)
        val letrasExtras = ('A'..'Z')
            .filterNot { letrasNombre.contains(it) }
            .shuffled()
            .take(masLetras)

        // 3. Unimos letras del nombre + extras y mezclamos
        return (letrasNombre + letrasExtras).shuffled()
    }

    fun seleccionarLetra(letra: Char, index: Int) {
        val estado = _uiStateLetras.value
        val totalLetrasSinEspacios = estado.nombreCorrecto.count { it != ' ' }
        val letrasSeleccionadasActuales = estado.letrasSeleccionadas.size


        if (letrasSeleccionadasActuales < totalLetrasSinEspacios) {
            val nuevaLetra = LetraSeleccionada(letra, index)
            val nuevasLetras = estado.letrasSeleccionadas + nuevaLetra

            _uiStateLetras.value = estado.copy(
                letrasSeleccionadas = nuevasLetras
            )
        } else {
            Log.d("AyudaLetras", "No se puede seleccionar más letras, ya se alcanzó el máximo")
        }

//        val estado = _uiStateLetras.value
//        val totalLetrasSinEspacios = estado.nombreCorrecto.count { it != ' ' }
//
//        if (estado.letrasSeleccionadas.size < totalLetrasSinEspacios) {
//            _uiStateLetras.value = estado.copy(
//                letrasSeleccionadas = estado.letrasSeleccionadas + LetraSeleccionada(letra, index)
//            )
//        }
    }

    fun limpiarUltimaLetra() {
        val estado = _uiStateLetras.value
        _uiStateLetras.value = estado.copy(
            letrasSeleccionadas = estado.letrasSeleccionadas.dropLast(1)
        )
    }

    fun dividirEnFilas(letras: List<Char>, maxPorFila: Int = 6): List<List<Char>> {
        return letras.chunked(maxPorFila)
    }

    fun seleccionarLetraAyuda() {
        val estado = _uiStateLetras.value
        val nombreSinEspacios = estado.nombreCorrecto.filter { it != ' ' }
        val posicionActual = estado.letrasSeleccionadas.size
        // Verificar si ya completamos el nombre
        if (posicionActual >= nombreSinEspacios.length) {
            Log.d("AyudaLetras", "Ya se completaron todas las letras necesarias")
            return
        }

        // Obtener la siguiente letra que debemos seleccionar (convertir a mayúscula)
        val siguienteLetra = nombreSinEspacios[posicionActual].uppercaseChar()

        // Buscar todas las posiciones donde aparece la siguienteLetra en el pool
        val indicesLetras = estado.poolLetras
            .mapIndexed { index, letra -> index to letra }
            .filter { (_, letra) -> letra.uppercaseChar() == siguienteLetra }
            .map { (index, _) -> index }

        // Obtener las posiciones ya seleccionadas
        val indicesYaSeleccionados = estado.letrasSeleccionadas.map { it.indiceEnPool }

        // Encontrar la primera posición que aún no ha sido seleccionada
        val indexEnPool = indicesLetras.firstOrNull { index ->
            !indicesYaSeleccionados.contains(index)
        }

        if (indexEnPool == null) {
            return
        }

        seleccionarLetra(estado.poolLetras[indexEnPool], indexEnPool)

        // Verificar el estado después de la selección
        val nuevoEstado = _uiStateLetras.value
    }
    //endregion


    //region Estados de tienda y topbar
    fun MostrarTopBar() {
        _mostrarTopBar.value = true
    }

    fun OcultarTopBar() {
        _mostrarTopBar.value = false
    }

    fun MostrarTienda() {
        _verTienda.value = true
    }

    fun OcultarTienda() {
        _verTienda.value = false
    }

    //endregion
    fun TerminarJuego() {
        _tiempoRestante.value = 0
    }

    fun ActivarPoder(poderId: String) {
        when (poderId) {
            "revelar_letras" -> {
                revelarLetrasCorrectas()
            }
            "congelar_tiempo" -> {
                congelarTiempo()
            }
            "puntaje_doble" -> {
                _uiStateLetras.update { it.copy(puntajeDobleActivado = 5) }
            }
        }
    }

    private fun revelarLetrasCorrectas() {
        val estado = _uiStateLetras.value
        val nombreSinEspacios = estado.nombreCorrecto.filter { it != ' ' }.uppercase()
        val indicesCorrectos = mutableSetOf<Int>()

        // Encontrar TODAS las letras correctas en el pool (no solo las siguientes)
        estado.poolLetras.forEachIndexed { index, letra ->
            // Verificar si esta letra aparece en cualquier posición del nombre
            if (nombreSinEspacios.contains(letra.uppercaseChar())) {
                indicesCorrectos.add(index)
            }
        }
        _uiStateLetras.update { it.copy(letrasReveladas = indicesCorrectos) }
    }

    // 1. Poder: Congelar tiempo
    private fun congelarTiempo() {
        viewModelScope.launch {
            _uiStateLetras.update { it.copy(tiempoCongelado = true) }
            delay(10_000L) // 10 segundos
            _uiStateLetras.update { it.copy(tiempoCongelado = false) }
        }
    }
}