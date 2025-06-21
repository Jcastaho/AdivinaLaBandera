package com.straccion.adivinalabandera.presentation.navigation.graph.select_dificultad

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.straccion.adivinalabandera.presentation.navigation.Graph
import com.straccion.adivinalabandera.presentation.navigation.screen.select_dificultad.SelectedDificultad
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.screens.dificultad.PantallaElegirDificultad


fun NavGraphBuilder.SelectDificultadNavGraph(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic
) {
    navigation(
        route = Graph.SELECCIONARDIFICULTAD,
        startDestination = SelectedDificultad.SeleccionarDificultad.route
    ){
        composable(
            route =  SelectedDificultad.SeleccionarDificultad.route,
            arguments = listOf(
                navArgument("juego") { type = NavType.StringType }
            )
        ){
            PantallaElegirDificultad(navHostController, viewModelMusic)
        }
    }
}