package com.straccion.adivinalabandera.domain.models

import java.util.UUID

data class ContadorState(
    val contadorActivo: Boolean = true,
    val numeroVisible: Int = 3
)

// Evento para animaciones
sealed class EventoAnimacion {
    object Ninguno : EventoAnimacion()
    object Acierto : EventoAnimacion()  // +2 segundos
    object Fallo : EventoAnimacion()    // -1 segundo
}

data class AnimacionEvento(
    val tipo: EventoAnimacion,
    val id: String = UUID.randomUUID().toString() // ID único para cada evento
)