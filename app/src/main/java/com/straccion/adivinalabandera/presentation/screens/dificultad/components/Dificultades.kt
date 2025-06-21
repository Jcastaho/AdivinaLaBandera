package com.straccion.adivinalabandera.presentation.screens.dificultad.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.ui.theme.Rojito

@Composable
fun Dificultades(
    navHostController: NavHostController,
    route: String,
    viewModelMusic: ViewModelMusic
) {
    Column(Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .width(150.dp),
            onClick = {
                val dificultadValor = 0 // Aquí defines el valor de dificultad
                val nuevaRuta = route.replace("{dificultad}", dificultadValor.toString()) // Reemplaza el placeholder
                navHostController.navigate(route = nuevaRuta)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Rojito, // Rosa para el botón
                contentColor = Color.White
            ),
        ) {
            Text(
                fontSize = 20.sp,
                text = stringResource(R.string.normal),
                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
            )
        }
        Spacer(Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .width(150.dp),
            onClick = {
                val dificultadValor = 1 // Aquí defines el valor de dificultad
                val nuevaRuta = route.replace("{dificultad}", dificultadValor.toString()) // Reemplaza el placeholder
                navHostController.navigate(route = nuevaRuta)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Rojito, // Rosa para el botón
                contentColor = Color.White
            ),
        ) {
            Text(
                fontSize = 20.sp,
                text = stringResource(R.string.dificil),
                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
            )
        }
    }
}