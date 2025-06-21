package com.straccion.adivinalabandera.presentation.screens.pantalla_inicio.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.navigation.screen.selected_game.SelectedGame
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.navigation.screen.tienda_inicio.TiendaInicio
import com.straccion.adivinalabandera.presentation.popups.avatarpopup.AvatarInfoPopup
import com.straccion.adivinalabandera.presentation.popups.regalospopup.RegalosDesplegable
import com.straccion.adivinalabandera.presentation.popups.regalospopup.ViewModelRegalos
import com.straccion.adivinalabandera.presentation.popups.tiendapopup.tiendagemas.TiendaDesplegable
import com.straccion.adivinalabandera.presentation.screens.configuraciones.PantallaConfiguraciones
import com.straccion.adivinalabandera.presentation.popups.tiendapopup.tiendagemas.ViewModelTienda
import com.straccion.adivinalabandera.ui.theme.Rojito
import kotlinx.coroutines.delay

@Composable
fun Start(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser,
    viewModel: ViewModelPantallaStart = hiltViewModel(),
    viewModelTienda: ViewModelTienda = hiltViewModel(),
    viewModelRegalo: ViewModelRegalos = hiltViewModel(),
) {
   // val recompensaRecibidaTienda = viewModelTienda.recompensaRecibidaTienda.collectAsState()
    val recompensaRecibidaRegalo = viewModelRegalo.recompensaRecibidaAnuncio.collectAsState()
    val mostrarAvatar by viewModel.verAvatar.collectAsState()
    val verTienda by viewModel.verTienda.collectAsState()
    val verRegalos by viewModel.verRegalos.collectAsState()
    val isDarkMode = isSystemInDarkTheme()
    var isBouncing by remember { mutableStateOf(true) }
    val puedeClaim = viewModelRegalo.puedeClaimRegalo.collectAsState()

    val offsetY by animateFloatAsState(
        targetValue = if (isBouncing) -10f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    var mostrarConfiguraciones = viewModelMusic.verConfig.collectAsState()


    LaunchedEffect(Unit) {
        while (true) {
            isBouncing = !isBouncing
            delay(1000) // Sincroniza con el tiempo de la animación
        }
        viewModelTienda.cargarAnuncioRecompensado()
    }

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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(bottom = 20.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(65.dp)
                    .padding(15.dp)

                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // Opcional: quita el efecto de ripple si no lo quieres
                    ) {
                        viewModelMusic.MostrarConfig()
                    },
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = Color.White,
            )
            Icon(
                modifier = Modifier
                    .size(65.dp)
                    .padding(15.dp)

                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // Opcional: quita el efecto de ripple si no lo quieres
                    ) {
                        viewModel.MostrarAvatar()
                    },
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color.White,
            )
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .padding(15.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        viewModel.MostrarRegalos()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.CardGiftcard,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize()
                )

                // Badge de notificación (arriba a la derecha)
                if (!recompensaRecibidaRegalo.value && puedeClaim.value) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-10).dp)
                            .offset(y = offsetY.dp + 6.dp)
                            .size(20.dp)
                            .background(Color.Red, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(x = 0.dp, y = (-2).dp),
                            text = "!",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .padding(15.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        navHostController.navigate(TiendaInicio.AbrirTienda.route)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize()
                )

                // Badge de notificación (arriba a la derecha)
//                if (!recompensaRecibidaTienda.value) {
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.TopEnd)
//                            .offset(x = 4.dp, y = (-10).dp)
//                            .offset(y = offsetY.dp + 6.dp)
//                            .size(20.dp)
//                            .background(Color.Red, shape = CircleShape),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            modifier = Modifier
//                                .align(Alignment.Center)
//                                .offset(x = 0.dp, y = (-2).dp),
//                            text = "!",
//                            color = Color.White,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) { }

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .offset(y = offsetY.dp) // Aplica la animación de rebote
                    .padding(bottom = 115.dp),
                onClick = {
                    navHostController.navigate(route = SelectedGame.SeleccionarGame.route) //aqui se peude verificar
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Rojito, // Rosa para el botón
                    contentColor = Color.White
                )
            ) {
                Text(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                    color = Color.White,
                    text = stringResource(R.string.play)
                )
            }
        }
        if (mostrarConfiguraciones.value) {
            PantallaConfiguraciones(viewModelMusic, viewModeluser)
        }
        if (verTienda) {
            TiendaDesplegable(onClick = { viewModel.OcultarTienda() }, viewModeluser, viewModelTienda)
        }
        if (verRegalos) {
            RegalosDesplegable(onClick = { viewModel.OcultarRegalos() }, viewModeluser)
        }
        if (mostrarAvatar) {
            AvatarInfoPopup(onClick = { viewModel.OcultarAvatar() }, navHostController, viewModeluser)
        }
    }
}

