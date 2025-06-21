package com.straccion.adivinalabandera.domain.models

import com.straccion.adivinalabandera.R

data class AyudasPoderes(
    val poderId: String,
    val icono: Int,
    val descripcionPoder: String,
    val precio: Int
) {
    object AyudasPoderes {
        val listaPoderesJuego1 = listOf(
            AyudasPoderes(
                poderId = "ojo_de_halcon",
                icono = R.drawable.icon_vida,
                descripcionPoder = "Permite ver la imagen una segunda vez",
                precio = 10
            ),
            AyudasPoderes(
                poderId = "escudo",
                icono = R.drawable.icon_googlegames,
                descripcionPoder = "No se pierde una vida si fallas.",
                precio = 15
            ),
            AyudasPoderes(
                poderId = "eliminar_opcion",
                icono = R.drawable.icon_trofeo,
                descripcionPoder = "Elimina una de las 4 opciones, dejando solo 3",
                precio = 5
            ),
            AyudasPoderes(
                poderId = "puntaje_doble",
                icono = R.drawable.icon_editar,
                descripcionPoder = "Recibiras durante 5 turnos doble puntuacion",
                precio = 20
            )
        )
        val listaPoderesJuego2 = listOf(
            AyudasPoderes(
                poderId = "revelar_letras",
                icono = R.drawable.icon_vida,
                descripcionPoder = "Aparecen de color verde las letras que se deben usar para el nombre del personaje",
                precio = 10
            ),
            AyudasPoderes(
                poderId = "congelar_tiempo",
                icono = R.drawable.icon_gema,
                descripcionPoder = "Detiene el reloj por 10 segundos",
                precio = 15
            ),
            AyudasPoderes(
                poderId = "puntaje_doble",
                icono = R.drawable.icon_editar,
                descripcionPoder = "Recibiras durante 5 turnos doble puntuacion",
                precio = 20
            ),
        )
    }
}