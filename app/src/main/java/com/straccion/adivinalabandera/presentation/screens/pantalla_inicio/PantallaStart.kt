package com.straccion.adivinalabandera.presentation.screens.pantalla_inicio

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.straccion.adivinalabandera.core.Constants.AD_ID_BANNER
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.components.isInternetAvailable
import com.straccion.adivinalabandera.presentation.screens.pantalla_inicio.components.Start

@Composable
fun PantallaStart(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser
) {
    Scaffold(
        content = {
            Start(it, navHostController, viewModelMusic, viewModeluser)
        },
        bottomBar = {
            val context = LocalContext.current
            val configuration = LocalConfiguration.current

            // Obtener ancho en dp
            val adWidth = configuration.screenWidthDp
            val tieneInternet = remember { isInternetAvailable(context) }
            if (tieneInternet) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    AndroidView(factory = { contexto ->
                        val adView = AdView(contexto).apply {
                            // Adaptive Banner
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