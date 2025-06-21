package com.straccion.adivinalabandera.presentation.navigation.screen.tienda_inicio

sealed class TiendaInicio(val route: String) {
    data object AbrirTienda: TiendaInicio("tienda_inicio")
}