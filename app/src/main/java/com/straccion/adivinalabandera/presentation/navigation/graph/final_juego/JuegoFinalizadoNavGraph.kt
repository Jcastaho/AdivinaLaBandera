package com.straccion.adivinalabandera.presentation.navigation.graph.final_juego

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.straccion.adivinalabandera.presentation.navigation.Graph
import com.straccion.adivinalabandera.presentation.navigation.screen.final_juego.FinalJuego
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.screens.pantalla_juego_finalizado.JuegoFinalizado


fun NavGraphBuilder.JuegoFinalizadoNavGraph(
    navHostController: NavHostController,
    viewModeluser: ViewModelGestionarUser,
    viewModelMusic: ViewModelMusic
) {
    navigation(
        route = Graph.JUEGOFINALIZADO,
        startDestination = FinalJuego.PantallaJuegoFinalizado.route
    ){
        composable(
            route =  FinalJuego.PantallaJuegoFinalizado.route,
            arguments = listOf(
                navArgument("idJuego") { type = NavType.StringType },
                navArgument("puntuacion") { type = NavType.IntType },
                navArgument("dificultad") { type = NavType.IntType }
            )
        ){
            JuegoFinalizado(navHostController, viewModeluser, viewModelMusic)
        }
    }
}