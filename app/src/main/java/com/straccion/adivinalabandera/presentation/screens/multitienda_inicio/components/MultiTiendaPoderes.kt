package com.straccion.adivinalabandera.presentation.screens.multitienda_inicio.components

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.domain.models.AyudasPoderes
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser

@Composable
fun MultiTiendaPoderes(
    paddingValues: PaddingValues,
    viewModeluser: ViewModelGestionarUser,
    gemas: Int
) {
    val juegos = listOf("¿Quien era?", "Adivina el personaje", "Cartas")
    var juegoSeleccionado by remember { mutableStateOf<String?>(null) }
    val poderesPorJuego = mapOf(
        "¿Quien era?" to AyudasPoderes.AyudasPoderes.listaPoderesJuego1,
        "Adivina el personaje" to AyudasPoderes.AyudasPoderes.listaPoderesJuego2,
        "Cartas" to emptyList()
    )
    val context = LocalContext.current
    var poderSeleccionado by remember { mutableStateOf<AyudasPoderes?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    // Paleta de colores
    val colorBorde = Color(0xFF4CAF50)

    // Animación de elevación
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(if (isPressed) 8.dp else 2.dp)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(juegos) { juego ->
            val poderes = poderesPorJuego[juego] ?: emptyList()

            if (poderes.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 30.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0B2))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 12.dp),
                            text = juego.uppercase(),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                            letterSpacing = 1.sp,
                            fontSize = 23.sp
                        )
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 500.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(poderes) { poder ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(0.9f)
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = null
                                        ) {
                                            poderSeleccionado = poder
                                            mostrarDialogo = true
                                            juegoSeleccionado = juego
                                        },
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = elevation),
                                    border = BorderStroke(1.dp, colorBorde),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(
                                            0xFFFFE0B2
                                        )
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(15.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(CircleShape)
                                                .border(1.dp, colorBorde, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                modifier = Modifier.size(40.dp),
                                                painter = painterResource(poder.icono),
                                                contentDescription = ""
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        Text(
                                            text = poder.poderId.replace("_", " ")
                                                .replaceFirstChar { it.uppercase() },
                                            color = Color.Black,
                                            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                            fontSize = 15.sp,
                                            textAlign = TextAlign.Center
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = "${poder.precio} monedas",
                                            fontSize = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo para mostrar detalles del poder
    if (mostrarDialogo && poderSeleccionado != null) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
                poderSeleccionado = null
            },
            confirmButton = {
                TextButton(onClick = {
                    if (gemas > poderSeleccionado!!.precio) {
                        viewModeluser.PoderesActualizar(
                            poderSeleccionado!!.precio,
                            false,
                            juegoSeleccionado!!,
                            poderSeleccionado!!.poderId,
                            true
                        )
                        Toast.makeText(
                            context,
                            "Poder Comprado correctamente, se ha almacenado en el inventario",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(context, "No tienes suficientes gemas", Toast.LENGTH_SHORT)
                            .show()
                    }
                    mostrarDialogo = false
                }) {
                    Text("Comprar", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarDialogo = false
                }) {
                    Text("Cerrar")
                }
            },
            title = {
                Text(
                    text = "Poder:  ${
                        poderSeleccionado?.poderId?.replace("_", " ")
                            ?.replaceFirstChar { it.uppercase() }
                    }",
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = poderSeleccionado!!.icono),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(bottom = 12.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = poderSeleccionado!!.descripcionPoder,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        Text(
                            text = "Precio: ${poderSeleccionado!!.precio} ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                        Image(
                            modifier = Modifier
                                .size(25.dp),
                            painter = painterResource(R.drawable.icon_gema),
                            contentDescription = null
                        )
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}
