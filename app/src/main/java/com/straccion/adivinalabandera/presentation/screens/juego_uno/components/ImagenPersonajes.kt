package com.straccion.adivinalabandera.presentation.screens.juego_uno.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.straccion.adivinalabandera.domain.models.Personaje

@Composable
fun ImagenPersonajes(
    personaje: Personaje
) {
    Image(
        painter = painterResource(id = personaje.imagenId),
        contentDescription = "Imagen del personaje",
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 20.dp)
    )

}