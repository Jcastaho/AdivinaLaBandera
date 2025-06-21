package com.straccion.adivinalabandera.presentation.navigation.graph.select_game

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.straccion.adivinalabandera.presentation.navigation.Graph
import com.straccion.adivinalabandera.presentation.navigation.screen.selected_game.SelectedGame
import com.straccion.adivinalabandera.presentation.screens.pantalla_seleccion_juego.PantallaSeleccionarJuego


fun NavGraphBuilder.SelectGameNavGraph(
    navHostController: NavHostController
) {
    navigation(
        route = Graph.SELECCIONARJUEGO,
        startDestination = SelectedGame.SeleccionarGame.route
    ){
        composable(
            route =  SelectedGame.SeleccionarGame.route
        ){
            PantallaSeleccionarJuego(navHostController)
        }
    }
}