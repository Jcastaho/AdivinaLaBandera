package com.straccion.adivinalabandera.presentation.screens.juego_uno.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.straccion.adivinalabandera.presentation.screens.juego_uno.PantallaJuegoPrimeroViewModel
import com.straccion.adivinalabandera.ui.theme.Griscito


@Composable
fun ContadorJuegoPrincipal(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser,
    viewModel: PantallaJuegoPrimeroViewModel = viewModel()
) {
    val isDarkMode = isSystemInDarkTheme()

    // Estado local para el contador
    val contadorState by viewModel.contadorState.collectAsState()
    val isMuted = viewModelMusic.isMute.collectAsState()

    // Animaciones para el contador
    val scale by animateFloatAsState(
        targetValue = if (contadorState.numeroVisible > 0) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
    LaunchedEffect(Unit) {
        if (isMuted.value){
            viewModelMusic.changeMusic(R.raw.musica_juego_uno)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize()
                .graphicsLayer { if (isDarkMode) alpha = 0.08f },
            painter = painterResource(R.drawable.portada),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        // Capa oscura SOLO si el modo oscuro está desactivado
        if (!isDarkMode) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Ajusta la opacidad según necesites
            )
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (contadorState.numeroVisible > 0) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
    )


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (contadorState.contadorActivo) {
            if (contadorState.numeroVisible > 0) {
                Text(
                    text = contadorState.numeroVisible.toString(),
                    fontSize = 100.sp,
                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                    fontWeight = FontWeight.Bold,
                    color = Griscito,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale, alpha = alpha)
                )
            }
        } else {
            // Solo mostramos el juego cuando el contador ha terminado
            JuegoPrincipal(navHostController, viewModeluser)
        }
    }
}