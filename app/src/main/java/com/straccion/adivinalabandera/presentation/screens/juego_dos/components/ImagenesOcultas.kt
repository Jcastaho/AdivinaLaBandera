package com.straccion.adivinalabandera.presentation.screens.juego_dos.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.domain.models.Personaje
import com.straccion.adivinalabandera.presentation.screens.juego_dos.PantallaJuegoSegundoViewModel
import com.straccion.adivinalabandera.ui.theme.Rojito
import kotlinx.coroutines.delay

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImagenesOcultas(
    personaje: Personaje,
    areasVisibles: List<Int>,
    dificultad: Int,
    viewModel: PantallaJuegoSegundoViewModel = hiltViewModel()
) {
    val estado by viewModel.uiStateLetras.collectAsState()
    val filas =
        viewModel.dividirEnFilas(estado.poolLetras) // Puedes ajustar según el tamaño de poolLetras
    val imageSizeDp = 250.dp
    val scrollState = rememberScrollState()
    if (dificultad == 0) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(10_000L)
                val estadoActual = viewModel.uiStateLetras.value
                val letrasSeleccionadasCount = estadoActual.letrasSeleccionadas.size
                val totalLetrasNecesarias = estadoActual.nombreCorrecto.count { it != ' ' }

                Log.d(
                    "AyudaLetras",
                    "estadoActual.nombreCorrecto.length: ${estadoActual.nombreCorrecto.length}"
                )

                if (letrasSeleccionadasCount < totalLetrasNecesarias) {
                    viewModel.seleccionarLetraAyuda()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 15.dp), // Habilita el scroll vertical
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // Declaramos el estado correctamente (usando `by` para delegar)
            var imageSize by remember { mutableStateOf(IntSize.Zero) }

            Image(
                painter = painterResource(id = personaje.imagenId),
                contentDescription = "Imagen del personaje",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(imageSizeDp)
                    .onGloballyPositioned { coordinates ->
                        imageSize = coordinates.size // Actualizamos el estado
                    },
                contentScale = ContentScale.Crop
            )
            if (imageSize == IntSize.Zero) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .size(imageSizeDp)
                        .background(Color.Black),
                    contentAlignment = Alignment.TopCenter
                ) {}
            }
            if (imageSize != IntSize.Zero) {
                Box(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { imageSize.width.toDp() })
                        .height(with(LocalDensity.current) { imageSize.height.toDp() })
                ) {
                    // Configuración para 12 cuadrados (3 filas x 4 columnas)
                    val rows = 3
                    val cols = 4
                    val squareWidth = imageSize.width / cols
                    val squareHeight = imageSize.height / rows

                    repeat(rows) { row ->
                        repeat(cols) { col ->
                            val squareIndex = row * cols + col
                            // Solo dibujamos el cuadrado si NO está en areasVisibles
                            if (!areasVisibles.contains(squareIndex)) {
                                Box(
                                    modifier = Modifier
                                        .offset(
                                            x = with(LocalDensity.current) { (col * squareWidth).toDp() },
                                            y = with(LocalDensity.current) { (row * squareHeight).toDp() }
                                        )
                                        .width(with(LocalDensity.current) { squareWidth.toDp() })
                                        .height(with(LocalDensity.current) { squareHeight.toDp() })
                                        .background(Color.Black)
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

        // Casillas vacías donde se colocan las letras
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Calculamos dinámicamente el tamaño de los cuadros basado en el ancho disponible
            val itemSize = remember(maxWidth) {
                val minItemSize = 48.dp
                val maxItemsPerRow = 6 // Máximo deseable de cuadros por fila
                val calculatedSize =
                    maxWidth / maxItemsPerRow - 8.dp // Restamos espacio para padding

                calculatedSize.coerceAtLeast(minItemSize)
                    .coerceAtMost(80.dp) // Límites mínimo y máximo
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                var seleccionIndex = 0
                estado.nombreCorrecto.forEachIndexed { i, caracter ->
                    if (caracter == ' ') {
                        // Espacio más inteligente entre palabras
                        if (i > 0 && estado.nombreCorrecto[i - 1] != ' ') {
                            Spacer(modifier = Modifier.width(itemSize / 2))
                        }
                    } else {
                        val letraMostrada =
                            estado.letrasSeleccionadas.getOrNull(seleccionIndex)?.letra ?: ""
                        val esIncorrecta = seleccionIndex in estado.letrasIncorrectas
                        val borderColor = if (esIncorrecta) Color.Red else Color.White
                        Box(
                            modifier = Modifier
                                .size(itemSize)
                                .border(2.5.dp, borderColor)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = letraMostrada.toString(),
                                fontSize = (itemSize.value / 2).sp, // Tamaño de fuente relativo
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                maxLines = 1 // Asegura que el texto no baje a 2 líneas
                            )
                        }
                        seleccionIndex++

                        // Espacio entre letras (pero no después del último caracter)
                        if (i < estado.nombreCorrecto.length - 1 && estado.nombreCorrecto[i + 1] != ' ') {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        // Pool de letras clickeables
        filas.forEachIndexed { filaIndex, fila ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                fila.forEachIndexed { indexEnFila, letra ->
                    val indexEnPool = filaIndex * filas[0].size + indexEnFila
                    val yaSeleccionada =
                        estado.letrasSeleccionadas.any { it.indiceEnPool == indexEnPool }
                    val esRevelada = estado.letrasReveladas.contains(indexEnPool)

                    if (!yaSeleccionada) {
                        Button(
                            onClick = { viewModel.seleccionarLetra(letra, indexEnPool) },
                            modifier = Modifier.size(45.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Rojito, // Rosa para el botón
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = letra.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                    fontSize = 17.sp
                                )
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.size(45.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp)) // Ocupa el lugar
                    Spacer(modifier = Modifier.height(5.dp)) // Ocupa el lugar
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.limpiarUltimaLetra() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Rojito, // Rosa para el botón
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.borrar_letra).uppercase())
        }
        if (estado.letrasSeleccionadas.size == estado.nombreCorrecto.count { it != ' ' }) {
            LaunchedEffect(estado.letrasSeleccionadas) {
                viewModel.verificarRespuesta()
            }
        }
    }
}
