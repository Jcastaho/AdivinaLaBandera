package com.straccion.adivinalabandera.presentation.popups.tiendapopup.tiendapoderes

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.straccion.adivinalabandera.domain.models.AyudasPoderes
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser

@Composable
fun TiendaPoderes(
    juegos: List<String> = listOf(),
    poderesPorJuego: Map<String, List<AyudasPoderes>>, // clave = nombre del juego
    modifier: Modifier = Modifier,
    gemas: Int,
    viewModeluser: ViewModelGestionarUser,
    juegoInicialSeleccionado: String? = null // 👈 nuevo parámetro opcional
) {
    var juegoSeleccionado by remember {
        mutableStateOf(juegoInicialSeleccionado ?: juegos.firstOrNull() ?: "")
    }
    var poderSeleccionado by remember { mutableStateOf<AyudasPoderes?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    val context = LocalContext.current
   Column(
       modifier = modifier.fillMaxWidth(),
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Spacer(Modifier.height(5.dp))
       Text(
           text = stringResource(R.string.tienda),
           fontSize = 25.sp,
           color = Color.White,
           fontWeight = FontWeight.Bold
       )
       Spacer(Modifier.height(15.dp))
       Row(
           modifier = modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.spacedBy(16.dp),
           verticalAlignment = Alignment.CenterVertically
       ) {
           // Botones de juego a la izquierda
           Column(
               modifier = Modifier.wrapContentHeight(),
               verticalArrangement = Arrangement.spacedBy(12.dp),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               juegos.forEach { juego ->
                   val estaSeleccionado = juego == juegoSeleccionado
                   Box(
                       modifier = Modifier
                           .width(105.dp)
                           .height(70.dp)
                           .clip(RoundedCornerShape(12.dp))
                           .background(Color.Black)
                           .border(
                               width = 2.dp,
                               color = if (estaSeleccionado) Color.White else Color.Gray,
                               shape = RoundedCornerShape(12.dp)
                           )
                           .clickable {
                               juegoSeleccionado = juego // ← aquí cambiamos el estado local
                           },
                       contentAlignment = Alignment.Center
                   ) {
                       Text(
                           text = juego,
                           color = Color.White,
                           fontSize = 13.sp,
                           textAlign = TextAlign.Center
                       )
                   }
               }
               Spacer(Modifier.height(15.dp))
           }
           // Poderes del juego seleccionado a la derecha
           val poderes = poderesPorJuego[juegoSeleccionado] ?: emptyList()

           LazyVerticalGrid(
               columns = GridCells.Fixed(2),
               modifier = Modifier.weight(1f),
               horizontalArrangement = Arrangement.spacedBy(12.dp),
               verticalArrangement = Arrangement.spacedBy(12.dp),
               contentPadding = PaddingValues(8.dp)
           ) {
               items(poderes.size) { index  ->
                   val poder = poderes[index]
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally
                   ) {
                       Box(
                           modifier = Modifier
                               .size(100.dp)
                               .clip(RoundedCornerShape(8.dp))
                               .background(Color.DarkGray)
                               .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                               .clickable {
                                   poderSeleccionado = poder
                                   mostrarDialogo = true
                               },
                           contentAlignment = Alignment.Center
                       ) {
                           Icon(
                               painter = painterResource(poder.icono),
                               contentDescription = poder.descripcionPoder,
                               tint = Color.White,
                               modifier = Modifier.size(45.dp)
                           )
                       }
                       Spacer(modifier = Modifier.height(8.dp))

                       Text(
                           text = "Precio: ${poder.precio}",
                           fontSize = 10.sp,
                           color = Color.White
                       )
                   }
               }
           }
       }
       Spacer(Modifier.height(15.dp))
   }
    if (mostrarDialogo && poderSeleccionado != null) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
                poderSeleccionado = null
            },
            confirmButton = {
                TextButton(onClick = {
                    if (gemas > poderSeleccionado!!.precio){
                        viewModeluser.PoderesActualizar(poderSeleccionado!!.precio, false, juegoSeleccionado, poderSeleccionado!!.poderId, true)
                        Toast.makeText(context, "Poder Comprado correctamente, se ha almacenado en el inventario", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context, "No tienes suficientes gemas", Toast.LENGTH_SHORT).show()
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

