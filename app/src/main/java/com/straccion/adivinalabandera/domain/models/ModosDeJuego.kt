package com.straccion.adivinalabandera.domain.models

import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.navigation.screen.juego_dos.JuegoSegundo
import com.straccion.adivinalabandera.presentation.navigation.screen.juego_uno.JuegoPrimero

data class ModosDeJuego(
    val imageRes: Int, // ID del recurso drawable
    val route: String,  // Ruta a la que navegará al hacer clic
    val titulo: String
){
    object Modos {
        val images = listOf(
            ModosDeJuego(R.drawable.primerjuego_portada, JuegoPrimero.PantallaJuegoPrimero.route, "¿Cual era la bandera?"),
            ModosDeJuego(R.drawable.segundojuego_portada, JuegoSegundo.PantallaJuegoSegundo.route, "Adivina la bandera"),
            ModosDeJuego(R.drawable.imagen_prox, "", "")
        )
    }
}
