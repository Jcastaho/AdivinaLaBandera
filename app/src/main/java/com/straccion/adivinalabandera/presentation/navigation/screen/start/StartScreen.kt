package com.straccion.adivinalabandera.presentation.navigation.screen.start

sealed class StartScreen(val route: String) {
    data object PantallaInicio: StartScreen("start")
}
