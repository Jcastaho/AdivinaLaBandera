package com.straccion.adivinalabandera.domain.models

data class JuegoUiState(
    val mostrarTexto: Boolean = true,
    val mostrarImagen: Boolean = false,
    val mostrarPregunta: Boolean = false,
    val personajeActual: Personaje? = null,
    val respuestas: List<String> = emptyList(),
    val puntuacion: Int = 0,
    /////////////////
    val escudoActivo: Boolean = false, // escudo
    val turnosDoblePuntaje: Int = 0 // puntaje doble
)