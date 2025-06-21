package com.straccion.adivinalabandera.domain.models

data class Carta(
    val id: String,
    val imagenId: Int,
    val estaDescubierta: Boolean = false,
    val haSidoEmparejada: Boolean = false
)
