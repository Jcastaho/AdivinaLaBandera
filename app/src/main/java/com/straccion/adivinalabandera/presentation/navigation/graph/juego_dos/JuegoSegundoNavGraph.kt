package com.straccion.adivinalabandera.presentation.navigation.graph.juego_dos

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.straccion.adivinalabandera.presentation.navigation.Graph
import com.straccion.adivinalabandera.presentation.navigation.screen.juego_dos.JuegoSegundo
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.screens.juego_dos.PantallaJuegoSegundo

fun NavGraphBuilder.JuegoSegundoNavGraph(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser
) {
    navigation(
        route = Graph.JUEGOSEGUNDO,
        startDestination = JuegoSegundo.PantallaJuegoSegundo.route
    ){
        composable(
            route = JuegoSegundo.PantallaJuegoSegundo.route,
            arguments = listOf(
                navArgument("dificultad") { type = NavType.StringType }
            )
        ){
            PantallaJuegoSegundo(navHostController, viewModelMusic, viewModeluser)
        }
    }
}