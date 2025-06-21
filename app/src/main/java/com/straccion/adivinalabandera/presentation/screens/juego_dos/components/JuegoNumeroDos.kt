package com.straccion.adivinalabandera.presentation.screens.juego_dos.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.navigation.Graph
import com.straccion.adivinalabandera.presentation.navigation.screen.final_juego.FinalJuego
import com.straccion.adivinalabandera.presentation.screens.juego_dos.PantallaJuegoSegundoViewModel

@Composable
fun JuegoNumeroDos(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser,
    viewModel: PantallaJuegoSegundoViewModel = viewModel()
) {
    val uiState by viewModel.uiStateLetras.collectAsState()

    val tiempoRestante by viewModel.tiempoRestante.collectAsState()
    val dificultadJuego = viewModel.dificultadJuego


    // Animación para desvanecer el texto de introducción
    val alpha by animateFloatAsState(
        targetValue = if (uiState.mostrarTexto) 1f else 0f,
        animationSpec = tween(durationMillis = 4000, easing = LinearEasing)
    )

    LaunchedEffect(tiempoRestante) {
        if (tiempoRestante == 0) {
            viewModelMusic.pause()
            viewModel.OcultarTopBar()
            navHostController.navigate(
                FinalJuego.PantallaJuegoFinalizado.passJuego(
                    "juego2",
                    uiState.puntuacion,
                    dificultadJuego
                )
            ) {
                popUpTo(Graph.SELECCIONARDIFICULTAD) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (tiempoRestante > 0) {
            when {
                uiState.mostrarTexto -> {
                    IntroduccionJuegoSegundo(alpha)
                }

                uiState.mostrarImagen -> {
                    uiState.personajeActual?.let { personaje ->
                        uiState.areasVisibles.let { areas ->
                            viewModel.MostrarTopBar()
                            ImagenesOcultas(personaje, areas, dificultadJuego)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun IntroduccionJuegoSegundo(alpha: Float) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.graphicsLayer(alpha = alpha),
            text = stringResource(R.string.adivina_el_personaje).uppercase(),
            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.graphicsLayer(alpha = alpha),
            text = stringResource(R.string.descripcion1_juego2)+"\n" + stringResource(R.string.descripcion2_juego2),
            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}



