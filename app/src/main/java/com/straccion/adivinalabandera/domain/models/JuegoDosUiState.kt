package com.straccion.adivinalabandera.domain.models

data class JuegoDosUiState(
    var nombreCorrecto: String = "",
    var letrasSeleccionadas: List<LetraSeleccionada> = emptyList(),
    var poolLetras: List<Char> = emptyList(),
    val letrasIncorrectas: Set<Int> = emptySet(), // Índices de las letras incorrectas
    val letrasReveladas: Set<Int> = emptySet(), // Índices de letras reveladas PODERESSSSSSS
    val tiempoCongelado: Boolean = false,
    val puntajeDobleActivado:  Int = 0,
    val puntuacion: Int = 0,
    val mostrarTexto: Boolean = true,
    val mostrarImagen: Boolean = false,
    val mostrarPregunta: Boolean = false,
    val personajeActual: Personaje? = null,
    val areasVisibles: List<Int> = emptyList(), // <- nuevas áreas visibles
    val respuestas: List<String> = emptyList(),
)

data class LetraSeleccionada(
    var letra: Char,
    var indiceEnPool: Int
)