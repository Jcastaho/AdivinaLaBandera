package com.straccion.adivinalabandera.presentation.navigation.screen.select_dificultad

import android.net.Uri


sealed class SelectedDificultad(val route: String) {
    data object SeleccionarDificultad: SelectedDificultad("dificultad_juego?juego={juego}"){
        fun passJuego(juego: String) =
            "dificultad_juego?juego=${Uri.encode(juego)}"
    }
}