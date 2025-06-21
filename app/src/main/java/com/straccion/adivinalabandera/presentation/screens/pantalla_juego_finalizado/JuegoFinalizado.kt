package com.straccion.adivinalabandera.presentation.screens.pantalla_juego_finalizado

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.core.Constants.AD_ID_BANNER
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.components.isInternetAvailable
import com.straccion.adivinalabandera.presentation.navigation.Graph
import com.straccion.adivinalabandera.presentation.navigation.screen.selected_game.SelectedGame
import com.straccion.adivinalabandera.presentation.screens.pantalla_juego_finalizado.components.JuegoFinalizadoVista
import com.straccion.adivinalabandera.ui.theme.Rojito


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun JuegoFinalizado(
    navHostController: NavHostController,
    viewModelRecibido: ViewModelGestionarUser,
    viewModelMusic: ViewModelMusic,
    viewModel: JuegoFinalizadoViewModel = hiltViewModel()
) {
    val isDarkMode = isSystemInDarkTheme()
    val mostrarPopup by viewModel.mostrarPopup.collectAsState()
    val ranking by viewModel.mostrarRanking.collectAsState()

    val puntuacion = viewModel.puntaje
    val idJuego = viewModel.idJuego
    val dificultad = viewModel.dificultad

    val isMuted = viewModelMusic.isMute.collectAsState()
    LaunchedEffect(Unit) {
        viewModelRecibido.ActualizarScore(puntuacion, idJuego, dificultad)
        viewModelRecibido.ObtenerRankingCompleto(idJuego, dificultad)
        if (isMuted.value) {
            viewModelMusic.changeMusic(R.raw.musica_menu)
        }
    }

    Scaffold(
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { if (isDarkMode) alpha = 0.65f },
                    painter = painterResource(R.drawable.portada),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                if (!isDarkMode) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.35f))
                    )
                }
            }
            if (mostrarPopup) {
                Popup(
                    alignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                Color.Black.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp),
                    ) {
                        Column(
                            Modifier.wrapContentWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = stringResource(R.string.juego_finalizado),
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(Modifier.height(15.dp))

                            Text(
                                text = stringResource(R.string.puntaje) + " $puntuacion",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(Modifier.height(10.dp))

                            Button(
                                modifier = Modifier
                                    .width(180.dp),
                                onClick = {
                                    viewModel.setMostrarPopup(false)
                                    navHostController.navigate(route = SelectedGame.SeleccionarGame.route) {
                                        popUpTo(Graph.SELECCIONARJUEGO) {
                                            inclusive = false
                                        }
                                        launchSingleTop = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Rojito, // Rosa para el botón
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    fontSize = 15.sp,
                                    text = stringResource(R.string.volver_menu),
                                    fontFamily = FontFamily(Font(R.font.fuente_luckiest))
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp, end = 20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                viewModel.setMostrarRanking(true)
                                viewModel.setMostrarPopup(false)
                            },
                        painter = painterResource(R.drawable.icon_trofeo),
                        contentDescription = "Ranking"
                    )
                }
            }
            if (ranking) {
                JuegoFinalizadoVista(viewModelRecibido, idJuego, dificultad)
            }
        },
        bottomBar = {
            val context = LocalContext.current
            val configuration = LocalConfiguration.current

            // Obtener ancho en dp
            val adWidth = configuration.screenWidthDp
            val tieneInternet = remember { isInternetAvailable(context) }
            if (tieneInternet) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    AndroidView(factory = { contexto ->
                        val adView = AdView(contexto).apply {
                            setAdSize(
                                AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                                    context, adWidth
                                )
                            )
                            adUnitId = AD_ID_BANNER
                            loadAd(AdRequest.Builder().build())
                        }
                        adView
                    })
                }
            }
        }
    )
}