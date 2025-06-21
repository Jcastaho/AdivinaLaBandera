package com.straccion.adivinalabandera.presentation.navigation.screen.juego_uno



sealed class JuegoPrimero(val route: String) {
    data object PantallaJuegoPrimero: JuegoPrimero("juego_uno?dificultad={dificultad}") {
        fun passDificultad(dificultad: Int) = "juego_uno?dificultad=$dificultad"
    }

}