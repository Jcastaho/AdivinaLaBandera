package com.straccion.adivinalabandera.presentation.screens.multitienda_inicio

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.domain.models.TiendaState
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.popups.tiendapopup.tiendagemas.ViewModelTienda
import com.straccion.adivinalabandera.presentation.screens.multitienda_inicio.components.MultiTiendaGemas
import com.straccion.adivinalabandera.presentation.screens.multitienda_inicio.components.MultiTiendaPerfil
import com.straccion.adivinalabandera.presentation.screens.multitienda_inicio.components.MultiTiendaPoderes
import com.straccion.adivinalabandera.ui.theme.Rojito

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultitiendaInicio(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser,
    viewModel: ViewModelTienda = hiltViewModel()
) {
    var seleccionado by remember { mutableStateOf(0) }
    val itemSeleccionado = TiendaState.itemsMenu[seleccionado]
    var gemas = viewModeluser.gemas.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarAnuncioRecompensado()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.offset(x = (-15).dp),
                            fontSize = 30.sp,
                            text = itemSeleccionado.titulo,
                            color = Rojito,
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
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.Transparent
                )
            )
        },
        content = {
            Box(Modifier.fillMaxSize()
                .background(Color(0xFFFFE0B2))
            ){
                if (itemSeleccionado.titulo == "Gemas"){
                    MultiTiendaGemas(it, viewModeluser)
                } else if (itemSeleccionado.titulo == "Poderes"){
                    MultiTiendaPoderes(it, viewModeluser, gemas.value)
                } else if (itemSeleccionado.titulo == "Perfil"){
                    MultiTiendaPerfil(it, gemas.value)
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Rojito)
                    .navigationBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.offset(y = (-20).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    TiendaState.itemsMenu.forEachIndexed { index, item ->
                        var isPressed by remember { mutableStateOf(false) }

                        val targetScale = when {
                            isPressed -> 0.9f
                            seleccionado == index -> 1.2f
                            else -> 1f
                        }

                        val scale by animateFloatAsState(
                            targetValue = targetScale,
                            label = "ScaleAnimation"
                        )

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .graphicsLayer(scaleX = scale, scaleY = scale)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            isPressed = true
                                            tryAwaitRelease()
                                            isPressed = false
                                            seleccionado = index
                                        }
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(item.icono),
                                contentDescription = item.titulo,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    )
}