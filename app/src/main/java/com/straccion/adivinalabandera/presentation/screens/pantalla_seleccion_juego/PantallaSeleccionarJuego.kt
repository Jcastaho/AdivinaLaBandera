package com.straccion.adivinalabandera.presentation.screens.pantalla_seleccion_juego

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.screens.pantalla_seleccion_juego.components.InfiniteImageCarousel

@Composable
fun PantallaSeleccionarJuego(
    navHostController: NavHostController
) {
    val isDarkMode = isSystemInDarkTheme()

    Scaffold(
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize()
                        .graphicsLayer { if (isDarkMode) alpha = 0.65f },
                    painter = painterResource(R.drawable.portada),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                if (!isDarkMode) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.35f)) // Ajusta la opacidad según necesites
                    )
                }
            }
            InfiniteImageCarousel(it, navHostController)
        }
    )
}