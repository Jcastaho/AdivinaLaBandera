package com.straccion.adivinalabandera.presentation.screens.pantalla_seleccion_juego.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.domain.models.ModosDeJuego
import com.straccion.adivinalabandera.presentation.navigation.screen.select_dificultad.SelectedDificultad
import com.straccion.adivinalabandera.ui.theme.Griscito
import com.straccion.adivinalabandera.ui.theme.Rojito
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


@Composable
fun InfiniteImageCarousel(
    paddingValues: PaddingValues,
    navHostController: NavHostController
) {
    val pagerState = rememberPagerState(pageCount = { ModosDeJuego.Modos.images.size })
    LaunchedEffect(pagerState) {
        while (true) {
            delay(8000) // Espera antes de cambiar la página
            val nextPage =
                if (pagerState.currentPage == ModosDeJuego.Modos.images.size - 1) 0 else pagerState.currentPage + 1
            pagerState.animateScrollToPage(nextPage)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = 45.sp,
            text = stringResource(R.string.Seleccionar_modo_juego),
            color = Griscito,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
            textAlign = TextAlign.Center,
            softWrap = true,
            lineHeight = 75.sp
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 35.dp, end = 35.dp, top = 100.dp, bottom = 80.dp)
        ) { page ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val item = ModosDeJuego.Modos.images[page]
                val isDisabled = page == 2 //bloquea el ultimo modo
                Text(
                    fontSize = 28.sp,
                    text = item.titulo,
                    color = Griscito,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                    textAlign = TextAlign.Center,
                    softWrap = true
                )
                Spacer(Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset =
                                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                            alpha =
                                lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                        }
                        .fillMaxWidth()
                        .then(
                            if (!isDisabled) Modifier.clickable {
                                val ruta = SelectedDificultad.SeleccionarDificultad.passJuego(item.route)

                                navHostController.navigate(
                                    route = ruta
                                )
                            } else Modifier // <-- no clickable
                        ), // Al hacer clic, se selecciona la imagen actual
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { if (isDisabled) alpha = 0.5f }, // mas gris
                        painter = painterResource(item.imageRes),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        colorFilter = if (isDisabled) ColorFilter.colorMatrix(ColorMatrix().apply {
                            setToSaturation(
                                0f
                            )
                        }) else null // <-- escala de grises
                    )
                }
            }

        }
    }
}