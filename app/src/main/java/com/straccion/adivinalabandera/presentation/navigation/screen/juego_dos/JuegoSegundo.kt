package com.straccion.adivinalabandera.presentation.navigation.screen.juego_dos

sealed class JuegoSegundo(val route: String) {
    data object PantallaJuegoSegundo: JuegoSegundo("juego_dos?dificultad={dificultad}") {
        fun passDificultad(dificultad: Int) = "juego_dos?dificultad=$dificultad"
    }
}