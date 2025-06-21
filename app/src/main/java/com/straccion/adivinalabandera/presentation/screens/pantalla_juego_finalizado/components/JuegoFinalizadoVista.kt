package com.straccion.adivinalabandera.presentation.screens.pantalla_juego_finalizado.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.straccion.adivinalabandera.domain.models.Response
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.components.DefaultProgressBar
import com.straccion.adivinalabandera.R

@Composable
fun JuegoFinalizadoVista(
    viewModel: ViewModelGestionarUser,
    idJuego: String,
    dificultad: Int
) {
    val listaUsuarios by viewModel.listaUsuarios.collectAsState()
    val playerId = viewModel.playerId

    when (val response = listaUsuarios) {

        is Response.Loading -> {
            DefaultProgressBar()
        }

        is Response.Success -> {  // Nota el <*> aquí
            var topMejoresPuntuaciones = viewModel.ObtenerTop20Usuarios(response.data, idJuego, dificultad)
            val puestoUserActual = viewModel.ObtenerPuestoUsuario(response.data, playerId, idJuego, dificultad)
            JuegoFinalizadoPopup(topMejoresPuntuaciones, puestoUserActual!!, viewModel)
        }

        is Response.Failure -> {  // Nota el <*> aquí
            Text(
                text = stringResource(R.string.error_ranking),
                color = Color.Red
            )
        }
    }
}