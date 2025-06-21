package com.straccion.adivinalabandera.presentation.navigation.screen.selected_game

sealed class SelectedGame(val route: String) {
    data object SeleccionarGame: SelectedGame("selected_game")
}