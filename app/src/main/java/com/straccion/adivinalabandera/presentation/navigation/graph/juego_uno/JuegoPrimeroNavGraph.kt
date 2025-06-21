package com.straccion.adivinalabandera.presentation.navigation.graph.juego_uno

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.straccion.adivinalabandera.presentation.navigation.Graph
import com.straccion.adivinalabandera.presentation.navigation.screen.juego_uno.JuegoPrimero
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.screens.juego_uno.PantallaJuegoPrimero

fun NavGraphBuilder.JuegoPrimeroNavGraph(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser
) {
    navigation(
        route = Graph.JUEGOINICIAL,
        startDestination = JuegoPrimero.PantallaJuegoPrimero.route
    ){
        composable(
            route =  JuegoPrimero.PantallaJuegoPrimero.route,
            arguments = listOf(
                navArgument("dificultad") { type = NavType.StringType }
            )
        ){
            PantallaJuegoPrimero(navHostController, viewModelMusic, viewModeluser)
        }
    }
}