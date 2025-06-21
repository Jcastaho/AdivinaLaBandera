package com.straccion.adivinalabandera.domain.models

import com.straccion.adivinalabandera.R

data class TiendaState(
    val icono: Int,
    val titulo: String
) {
    companion object {
        val itemsMenu = listOf(
            TiendaState(R.drawable.icon_gema, "Gemas"),
            TiendaState(R.drawable.portada, "Poderes"),
            TiendaState(R.drawable.icon_trofeo, "Perfil")
        )
    }
}