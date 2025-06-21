package com.straccion.adivinalabandera.presentation.screens.dificultad

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.screens.dificultad.components.Dificultades
import com.straccion.adivinalabandera.ui.theme.Griscito
import com.straccion.adivinalabandera.ui.theme.Rojito

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PantallaElegirDificultad(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModel: PantallaElegirDificultadViewModel = viewModel()
) {
    val route = viewModel.rutaJuego
    val isDarkMode = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            modifier = Modifier.offset(x = (-15).dp),
                            fontSize = 35.sp,
                            text = stringResource(R.string.elegir_dificultad),
                            color = Griscito,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.popBackStack()
                        }

                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.Transparent
                )
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
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
                                .background(Color.Black.copy(alpha = 0.35f)) // Ajusta la opacidad según necesites
                        )
                    }
                }
                Dificultades(navHostController, route, viewModelMusic)
            }
        }
    )
}