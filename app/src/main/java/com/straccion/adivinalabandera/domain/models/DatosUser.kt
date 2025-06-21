package com.straccion.adivinalabandera.domain.models

data class DatosUser(
    var idUser: String = "",
    var nombre: String = "",
    var avatar: String = "",
    var monedas: Map<String, Gemas> = emptyMap(),
    var tipoJuegos: Map<String, Juego> = emptyMap()
){
    constructor() : this("", "", "")
}
data class Juego(
    var normal: Int = 0,
    var dificil: Int = 0
)

data class Gemas(
    var gemas: Int = 0
)