package com.straccion.adivinalabandera.presentation.navigation.graph.start

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.straccion.adivinalabandera.presentation.navigation.Graph
import com.straccion.adivinalabandera.presentation.navigation.screen.start.StartScreen
import com.straccion.adivinalabandera.presentation.navigation.screen.tienda_inicio.TiendaInicio
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.screens.multitienda_inicio.MultitiendaInicio
import com.straccion.adivinalabandera.presentation.screens.pantalla_inicio.PantallaStart

fun NavGraphBuilder.StartNavGraph(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser
) {
    navigation(
        route = Graph.START,
        startDestination = StartScreen.PantallaInicio.route
    ){
        composable(
            route =  StartScreen.PantallaInicio.route
        ){
            PantallaStart(navHostController, viewModelMusic, viewModeluser)
        }
        composable(
            route = TiendaInicio.AbrirTienda.route
        ){
            MultitiendaInicio(navHostController, viewModelMusic, viewModeluser)
        }
    }
}