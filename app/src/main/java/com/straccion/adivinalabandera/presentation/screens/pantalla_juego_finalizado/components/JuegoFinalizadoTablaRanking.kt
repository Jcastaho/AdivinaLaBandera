package com.straccion.adivinalabandera.presentation.screens.pantalla_juego_finalizado.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.straccion.adivinalabandera.R

@Composable
fun JuegoFinalizadoTablaRanking(
    nombre: String,
    imagen: String?,
    score: Int,
    puesto: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(35.dp),
                painter = painterResource(R.drawable.portada),
                contentDescription = ""
            )
            Spacer(Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = nombre,
                    color = Color.White,
                    fontSize = 14.sp,
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = stringResource(R.string.puntaje)+" $score",
                    color = Color.White,
                    fontSize = 14.sp,
                )
            }
            Spacer(Modifier.weight(1f)) // Esto empuja el puntaje "50" a la derecha
            Text(
                modifier = Modifier
                    .padding(4.dp),
                text = stringResource(R.string.puesto),
                color = Color.White,
                fontSize = 14.sp

            )
            Text(
                modifier = Modifier
                    .padding(end = 10.dp),
                text = puesto.toString(),
                color = Color.White,
                fontSize = 14.sp

            )
        }
    }
    Spacer(Modifier.height(12.dp))

}