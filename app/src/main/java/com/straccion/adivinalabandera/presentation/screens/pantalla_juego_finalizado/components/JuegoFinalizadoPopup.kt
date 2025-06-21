package com.straccion.adivinalabandera.presentation.screens.pantalla_juego_finalizado.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.straccion.adivinalabandera.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.adivinalabandera.domain.models.DatosUser
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.screens.pantalla_juego_finalizado.JuegoFinalizadoViewModel
import com.straccion.adivinalabandera.ui.theme.Rojito
import com.straccion.adivinalabandera.ui.theme.VerdecitoOsc

@Composable
fun JuegoFinalizadoPopup(
    topMejoresPuntuaciones: List<DatosUser>,
    puestoUserActual: Int,
    viewModelUser: ViewModelGestionarUser,
    viewModel: JuegoFinalizadoViewModel = hiltViewModel()
) {
    //val puestoYPuntaje = viewModelUser.obtenerPuestoYScoreUsuario(viewModelUser.userProfile.value, usuarios, idJuego, dificultad)
    val juego = viewModel.idJuego
    val dificultad = viewModel.dificultad
    val mostrarRanking by viewModel.mostrarRanking.collectAsState()
    val playerId = viewModelUser.playerId

    if (mostrarRanking) {
        Popup(
            alignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 100.dp)
                        .clickable {
                            viewModel.setMostrarRanking(false)
                            viewModel.setMostrarPopup(true)
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.ranking_de_jugadores),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                        color = Color.White
                    )

                    Spacer(Modifier.height(20.dp))
                    // Card que envuelve la lista scrollable
                    Card(
                        modifier = Modifier
                            .weight(1f)  // Ocupa el espacio disponible
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.DarkGray.copy(
                                alpha = 0.9f
                            )
                        ),
                        border = BorderStroke(2.dp, Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            LazyColumn {
                                items(topMejoresPuntuaciones.size) { index ->
                                    val usuario = topMejoresPuntuaciones[index]
                                    val tipoJuegos = usuario.tipoJuegos
                                    val juegoJugado = tipoJuegos[juego] as? Map<String, Any>
                                    val claveScore = if (dificultad == 0) "normal" else "dificil"
                                    val score = (juegoJugado?.get(claveScore) as? Long)?.toInt()
                                    JuegoFinalizadoTablaRanking(
                                        nombre = usuario.nombre,
                                        imagen = usuario.avatar,
                                        score = score!!,
                                        puesto = index + 1
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = VerdecitoOsc)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(35.dp)
                                        .clip(CircleShape),
                                    painter = painterResource(R.drawable.portada),
                                    contentDescription = ""
                                )
                                Spacer(Modifier.width(10.dp))
                                Column(
                                    verticalArrangement = Arrangement.SpaceAround
                                ) {
                                    val usuarioEncontrado =
                                        topMejoresPuntuaciones.find { it.idUser == playerId }
                                    if (usuarioEncontrado != null) {
                                        Text(
                                            modifier = Modifier.padding(top = 4.dp),
                                            text = usuarioEncontrado.nombre.toString(),
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                        )
                                    }
                                    if (usuarioEncontrado != null) {
                                        val tipoJuegos = usuarioEncontrado!!.tipoJuegos
                                        val juegoJugado = tipoJuegos[juego] as? Map<String, Any>
                                        val claveScore =
                                            if (dificultad == 0) "normal" else "dificil"
                                        val score = (juegoJugado?.get(claveScore) as? Long)?.toInt()
                                        Text(
                                            modifier = Modifier.padding(bottom = 4.dp),
                                            text = stringResource(R.string.puntaje) + " $score",
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                        )
                                    }
                                }
                                Spacer(Modifier.weight(1f)) // Esto empuja el puntaje "50" a la derecha
                                Text(
                                    modifier = Modifier
                                        .padding(4.dp),
                                    text = stringResource(R.string.puesto),
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )

                                Text(
                                    modifier = Modifier
                                        .padding(end = 10.dp),
                                    text = "$puestoUserActual",
                                    color = Color.Black,
                                    fontSize = 14.sp

                                )
                            }

                        }

                        Spacer(Modifier.height(20.dp))

                        Button(
                            modifier = Modifier.width(180.dp),
                            onClick = {
                                viewModel.setMostrarRanking(false)
                                viewModel.setMostrarPopup(true)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Rojito, // Rosa para el botón
                                contentColor = Color.White
                            )
                        ) {
                            Text(fontSize = 15.sp, text = stringResource(R.string.salir))
                        }
                    }
                }
            }
        }

    }
}