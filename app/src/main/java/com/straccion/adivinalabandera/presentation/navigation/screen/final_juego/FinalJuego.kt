package com.straccion.adivinalabandera.presentation.navigation.screen.final_juego

sealed class FinalJuego(val route: String) {
    data object PantallaJuegoFinalizado :
        FinalJuego("juego_finalizado?idJuego={idJuego}&puntuacion={puntuacion}&dificultad={dificultad}") {
        fun passJuego(idJuego: String, puntuacion: Int, dificultad: Int) =
            "juego_finalizado?idJuego=${idJuego}&puntuacion=${puntuacion}&dificultad=${dificultad}"
    }
}