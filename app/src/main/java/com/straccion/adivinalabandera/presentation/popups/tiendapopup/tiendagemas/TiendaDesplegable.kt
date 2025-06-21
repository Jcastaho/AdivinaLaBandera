package com.straccion.adivinalabandera.presentation.popups.tiendapopup.tiendagemas

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.ui.theme.Rojito


@Composable
fun TiendaDesplegable(
    onClick: (() -> Unit),
    viewModelGestionarUser: ViewModelGestionarUser,
    viewModel: ViewModelTienda
) {
    val context = LocalContext.current
    val actividad = context as? Activity
    val recompensaRecibida = viewModel.recompensaRecibidaTienda.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarAnuncioRecompensado()
    }
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onClick() } // Para cerrar al tocar fuera
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(0.9f)
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
                horizontalAlignment = Alignment.CenterHorizontally
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
                            text = stringResource(R.string.tienda_de_gemas).uppercase(),
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100) // Naranja oscuro para el título
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Para que la lista ocupe el espacio restante
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(5) { index -> // Ejemplo con 5 ítems como en la imagen
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
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
                                Image(
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(id = R.drawable.icon_gema), // Reemplaza con tu icono correcto
                                    contentDescription = "Gema"
                                )
                                Text(
                                    text = when (index) {
                                        0 -> "10"
                                        1 -> "200"
                                        2 -> "400"
                                        3 -> "700"
                                        4 -> "1000"
                                        else -> ""
                                    },
                                    color = Color(0xFF424242), // Gris oscuro para el texto
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                    fontSize = 16.sp
                                )
                                when (index) {
                                    0 -> Button(
                                        onClick = {
                                            if (actividad != null) {
                                                viewModel.mostrarAnuncio(actividad)
                                            }
                                        },
                                        enabled = !recompensaRecibida.value,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Rojito, // Rosa para el botón
                                            contentColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.anuncio).uppercase(),
                                            fontSize = 9.sp,
                                            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                        )
                                    }

                                    else -> Button(
                                        onClick = { /* TODO: Implementar lógica de compra */ },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Rojito, // Rosa para el botón
                                            contentColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = when (index) {
                                                1 -> "$1.99"
                                                2 -> "$3.98"
                                                3 -> "$4.98"
                                                4 -> "$6.98"
                                                else -> ""
                                            },
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Button(
                    modifier = Modifier.width(150.dp),
                    onClick = { onClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Rojito, // Rosa para el botón
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Cerrar",
                        fontSize = 14.sp
                    )
                }
            }
            if (recompensaRecibida.value) {
                viewModelGestionarUser.ActualizarGemas(5, true)
                Toast.makeText(context, "Recompensa recibida 5 Gemas", Toast.LENGTH_LONG).show()
            }
        }
    }
}