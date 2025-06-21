package com.straccion.adivinalabandera.presentation.screens.juego_uno.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.components.DefaultButton
import com.straccion.adivinalabandera.presentation.screens.juego_uno.PantallaJuegoPrimeroViewModel

@Composable
fun PantallaPreguntas(
    respuestas: List<String>,
    onRespuestaClick: (String) -> Unit,
    viewModeluser: ViewModelGestionarUser,
    viewModel: PantallaJuegoPrimeroViewModel = viewModel()
) {
    val scroll = rememberScrollState()
    var respuestaSeleccionada by remember { mutableStateOf<String?>(null) }
   // var mostrarTienda = viewModel.verTienda.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 35.dp)
    ) {
        // Contenedor para centrar la pregunta y respuestas
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-15).dp)
                .verticalScroll(scroll),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    contentColor = Color.Transparent,
                    containerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.descripcion2_juego1),
                        fontSize = 26.sp,
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(38.dp))
                    respuestas.forEach { respuesta ->
                        OpcionButton(
                            text = respuesta,
                            onClick = {
                                respuestaSeleccionada = respuesta
                                onRespuestaClick(respuesta)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

    }
//    if (mostrarTienda.value) {
//        TiendaDesplegable(onClick = { viewModel.OcultarTienda() }, viewModeluser)
//    }
}

@Composable
fun OpcionButton(
    text: String,
    onClick: () -> Unit
) {
    DefaultButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFD28E06),
                        Color(0xFFBAB107)
                    )
                )
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        text = text,
        fontSize = 16,
        onClick = { onClick() }
    )
}
