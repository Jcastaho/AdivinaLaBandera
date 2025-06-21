package com.straccion.adivinalabandera.presentation.navigation.graph.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.straccion.adivinalabandera.presentation.navigation.Graph
import com.straccion.adivinalabandera.presentation.navigation.graph.final_juego.JuegoFinalizadoNavGraph
import com.straccion.adivinalabandera.presentation.navigation.graph.juego_dos.JuegoSegundoNavGraph
import com.straccion.adivinalabandera.presentation.navigation.graph.juego_uno.JuegoPrimeroNavGraph
import com.straccion.adivinalabandera.presentation.navigation.graph.select_dificultad.SelectDificultadNavGraph
import com.straccion.adivinalabandera.presentation.navigation.graph.select_game.SelectGameNavGraph
import com.straccion.adivinalabandera.presentation.navigation.graph.start.StartNavGraph
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser
) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.START
    ){
        StartNavGraph(navHostController, viewModelMusic, viewModeluser)
        SelectGameNavGraph(navHostController)
        SelectDificultadNavGraph(navHostController, viewModelMusic)
        JuegoPrimeroNavGraph(navHostController, viewModelMusic, viewModeluser)
        JuegoSegundoNavGraph(navHostController, viewModelMusic, viewModeluser)
        JuegoFinalizadoNavGraph(navHostController, viewModeluser, viewModelMusic)
    }
}