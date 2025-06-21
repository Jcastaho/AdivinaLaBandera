package com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal

data class ObtenerDatosUseCases(
    val allDatosUser: ObtenerDatosUser,
    val obtenerDatosUserPorId: ObtenerDatosUserPorId,
    val guardarNuevoUser: GuardarNuevoUser,
    val actualizarScoresUser: ActualizarScoresUser,
    val actualizarGemasUser: ActualizarGemasUser,
    val obtenerGemasUser: ObtenerGemasUser,
    val actualizarPoderes: ActualizarPoderes,
    val obtenerPoderesJuego1: ObtenerPoderesJuego1,
    val obtenerPoderesJuego2: ObtenerPoderesJuego2
)