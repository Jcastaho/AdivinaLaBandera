package com.straccion.adivinalabandera.presentation.popups.regalospopup

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.ui.theme.Rojito


@Composable
fun RegalosDesplegable(
    onClick: (() -> Unit),
    viewModelGestionarUser: ViewModelGestionarUser,
    viewModel: ViewModelRegalos = hiltViewModel()
) {
    val mostrarRecibir = remember { mutableStateOf(false) }
    val recompensaRecibida = viewModel.recompensaRecibidaAnuncio.collectAsState()
    val puedeClaim = viewModel.puedeClaimRegalo.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.CargarAnuncioRecompensado()
    }
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onClick() } // Para cerrar al tocar fuera
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.6f)
                .padding(vertical = 30.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE0B2) // Un tono amarillo claro como fondo
            ),
            border = BorderStroke(8.dp, Rojito) // Borde naranja
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 26.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.regalos).uppercase(),
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100) // Naranja oscuro para el título
                        )
                    }
                }
                if (!recompensaRecibida.value && puedeClaim.value) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                mostrarRecibir.value = true
                            }
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFECB3) // Otro tono de amarillo claro para los items
                        ),
                        border = BorderStroke(1.dp, Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.regalos),
                                color = Color(0xFF424242), // Gris oscuro para el texto
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                fontSize = 16.sp
                            )
                            Icon(
                                imageVector = Icons.Default.CardGiftcard,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                } else {
                    Text(
                        text = stringResource(R.string.sin_regalos),
                        color = Color(0xFF424242), // Gris oscuro para el texto
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .width(150.dp),
                    onClick = { onClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Rojito, // Rosa para el botón
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.salir),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
    if (mostrarRecibir.value) {
        PupupAceptarRegalo(
            onClick = {
                mostrarRecibir.value = false
                viewModelGestionarUser.ActualizarGemas(5, true)
                viewModel.RegistrarClaimRegalo() // Registra el momento del claim
            },
            onClickCerrar = {
                mostrarRecibir.value = false
            },
            viewModelGestionarUser,
            viewModel
        )
    }
}

@Composable
fun PupupAceptarRegalo(
    onClick: () -> Unit,
    onClickCerrar: () -> Unit,
    viewModelGestionarUser: ViewModelGestionarUser,
    viewModel: ViewModelRegalos
) {
    var gemas = viewModelGestionarUser.gemas.collectAsState()
    val context = LocalContext.current
    val actividad = context as? Activity
    val recompensaRecibidaAnuncio = viewModel.recompensaRecibidaAnuncio.collectAsState()

    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { } // Para cerrar al tocar fuera
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE0B2) // Un tono amarillo claro como fondo
            ),
            border = BorderStroke(8.dp, Rojito) // Borde naranja
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.recibir).uppercase() + ":",
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100) // Naranja oscuro para el título
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (!recompensaRecibidaAnuncio.value) "5  " else "10  ",
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100) // Naranja oscuro para el título
                    )
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.icon_gema), // Reemplaza con tu icono correcto
                        contentDescription = "Gema"
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 26.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .width(110.dp),
                        onClick = {
                            if (actividad != null) {
                                viewModel.MostrarAnuncio(actividad)
                            }
                        },
                        enabled = !recompensaRecibidaAnuncio.value,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Rojito, // Rosa para el botón
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.duplicar),
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(
                        modifier = Modifier
                            .width(110.dp),
                        onClick = {
                            onClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Rojito, // Rosa para el botón
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.recibir),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        if (recompensaRecibidaAnuncio.value) {
            onClickCerrar()
            viewModel.RegistrarClaimRegalo() // Registra el momento del claim
            viewModelGestionarUser.ActualizarGemas(10, true)
            Toast.makeText(context, "10 gemas recibidas", Toast.LENGTH_SHORT).show()
        }
    }
}