package com.straccion.adivinalabandera.presentation.screens.juego_uno


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.core.Constants.AD_ID_BANNER
import com.straccion.adivinalabandera.domain.models.AyudasPoderes
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.presentation.components.DefaultFloatingActionButton
import com.straccion.adivinalabandera.presentation.components.TextoConEfectoLava
import com.straccion.adivinalabandera.presentation.components.isInternetAvailable
import com.straccion.adivinalabandera.presentation.popups.tiendapopup.tiendapoderes.TiendaPoderes
import com.straccion.adivinalabandera.presentation.screens.juego_uno.components.ContadorJuegoPrincipal
import com.straccion.adivinalabandera.ui.theme.Rojito
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaJuegoPrimero(
    navHostController: NavHostController,
    viewModelMusic: ViewModelMusic,
    viewModeluser: ViewModelGestionarUser,
    viewModel: PantallaJuegoPrimeroViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val mostrarTopBar by viewModel.mostrarTopBar.collectAsState()
    var vidas = viewModel.vidas.collectAsState()
    var puntuacion = uiState.puntuacion
    val vidaAnterior by viewModel.puntuacionAnterior.collectAsState()
    val defaultTextColor = MaterialTheme.colorScheme.onSurface
    var colorPuntuacion by remember { mutableStateOf(defaultTextColor) }
    val offsetXAnim = remember { Animatable(0f) }
    val gemas by viewModeluser.gemas.collectAsState()

    val estaPuntajeDoble = uiState.turnosDoblePuntaje > 0


    var menuAbierto by remember { mutableStateOf(false) }
    var mostrarBolsa by remember { mutableStateOf(false) }
    var mostrarTienda by remember { mutableStateOf(false) }
    val poderes by viewModeluser.poderesJuego1.collectAsState()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val poderesPorJuego = mapOf(
        "¿Quien era?" to AyudasPoderes.AyudasPoderes.listaPoderesJuego1,
        "Adivina el personaje" to AyudasPoderes.AyudasPoderes.listaPoderesJuego2,
        "Cartas" to emptyList() // Si aún no tienes
    )

    var poderSeleccionado by remember { mutableStateOf<AyudasPoderes?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    var mostrarDialogoSalir by remember { mutableStateOf(false) }

    BackHandler {
        mostrarDialogoSalir = true
    }
    //escucha los cambios de puntuacion para entrar al verde y los cambios de vida para entrar al rojo
    LaunchedEffect(puntuacion, vidas.value) {
        if (vidaAnterior != 0 || puntuacion != 0) {
            when {
                vidaAnterior == vidas.value -> {
                    // Si la puntuación aumentó, cambia a verde
                    colorPuntuacion = Color.Green
                    delay(1000) // Espera 1 segundo
                    colorPuntuacion = defaultTextColor // Vuelve al color original
                }

                vidas.value < vidaAnterior -> {
                    // Si la puntuación es igual, cambia a rojo y realiza la animación de zumbido
                    colorPuntuacion = Color.Red
                    offsetXAnim.snapTo(0f)
                    offsetXAnim.animateTo(
                        targetValue = 10f,
                        animationSpec = repeatable(
                            iterations = 5,
                            animation = tween(durationMillis = 50, easing = LinearEasing)
                        )
                    )
                    delay(1000)
                    colorPuntuacion = defaultTextColor
                }
            }
        }
        viewModeluser.CargarPoderesJuego1()
    }

    Scaffold(
        topBar = {
            if (mostrarTopBar) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 30.dp)
                ) {
                    // Puntuación en la parte superior
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Vidas a la izquierda
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.Start
                            ) {
                                repeat(vidas.value) {
                                    Image(
                                        modifier = Modifier.size(22.dp),
                                        painter = painterResource(R.drawable.icon_vida),
                                        contentDescription = null,
                                    )
                                    Spacer(Modifier.width(3.dp))
                                }
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .offset(offsetXAnim.value.dp),
                                text = stringResource(R.string.puntaje),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                color = colorPuntuacion
                            )
                            if (estaPuntajeDoble) {
                                TextoConEfectoLava(" X2", 20)
                            }
                            Text(
                                modifier = Modifier
                                    .offset(offsetXAnim.value.dp),
                                text = " $puntuacion",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                color = colorPuntuacion
                            )
                        }
                        Card(
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null, // Opcional: quita el efecto de ripple si no lo quieres
                                    onClick = { viewModel.MostrarTienda() }
                                )
                                .padding(16.dp),
                            shape = RoundedCornerShape(40.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.5f)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .wrapContentWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(28.dp),
                                    painter = painterResource(R.drawable.icon_gema),
                                    contentDescription = "Moneda",
                                )
                                Spacer(Modifier.width(15.dp))
                                Text(
                                    text = gemas.toString(),
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.width(10.dp))
                                Icon(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(Rojito.copy(alpha = 0.55f)),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Comprar monedas",
                                    tint = Color.LightGray
                                )
                            }
                        }
                    }
                }
            }
        },
        content = {
            ContadorJuegoPrincipal(navHostController, viewModelMusic, viewModeluser)
        },
        floatingActionButton = {
            if (vidas.value > 0) {
                when {
                    uiState.mostrarPregunta -> {
                        DefaultFloatingActionButton(
                            menuAbierto,
                            onClickMostrarBolsa = {
                                mostrarBolsa = true
                                menuAbierto = false
                                scope.launch {
                                    bottomSheetState.show()
                                }
                            },
                            onClickMostrarTienda = {
                                mostrarTienda = true
                                menuAbierto = false
                            },
                            onClickMostrarMenu = {
                                menuAbierto = !menuAbierto
                            }
                        )
                    }
                }
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
    if (mostrarBolsa) {
        Box(modifier = Modifier.fillMaxSize()) {
            ModalBottomSheet(
                onDismissRequest = {
                    mostrarBolsa = false
                },
                sheetState = bottomSheetState,
                containerColor = Color.Black,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.inventario),
                        fontSize = 24.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    // Ítems de la bolsa
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AyudasPoderes.AyudasPoderes.listaPoderesJuego1.forEach { poder ->
                            val cantidad = poderes[poder.poderId] ?: 0
                            val estaHabilitado = cantidad > 0
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.DarkGray)
                                        .border(
                                            2.dp,
                                            if (estaHabilitado) Color.White else Color.White.copy(
                                                alpha = 0.3f
                                            ),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable(enabled = estaHabilitado) {
                                            poderSeleccionado = poder
                                            mostrarDialogo = true
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = poder.icono),
                                        contentDescription = poder.descripcionPoder,
                                        tint = if (estaHabilitado) Color.White else Color.White.copy(
                                            alpha = 0.3f
                                        ),
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                Text(
                                    text = "x$cantidad",
                                    color = if (estaHabilitado) Color.White else Color.White.copy(
                                        alpha = 0.5f
                                    ),
                                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
    if (mostrarDialogo && poderSeleccionado != null) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
                poderSeleccionado = null
            },
            confirmButton = {
                TextButton(onClick = {
                    poderSeleccionado?.let { viewModel.ActivarPoder(it.poderId) }
                    viewModeluser.PoderesActualizar(
                        poderSeleccionado!!.precio,
                        false,
                        "¿Quien era?",
                        poderSeleccionado!!.poderId,
                        false
                    )
                    mostrarDialogo = false
                }) {
                    Text(
                        text = "Usar",
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarDialogo = false
                }) {
                    Text(
                        text = "Cerrar",
                        fontFamily = FontFamily(
                            Font(R.font.fuente_luckiest)
                        ),
                    )
                }
            },
            title = {
                Text(
                    text = "Poder:  ${
                        poderSeleccionado?.poderId?.replace("_", " ")
                            ?.replaceFirstChar { it.uppercase() }
                    }",
                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
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
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
    if (mostrarTienda) {
        Box(modifier = Modifier.fillMaxSize()) {
            ModalBottomSheet(
                onDismissRequest = { mostrarTienda = false },
                sheetState = bottomSheetState,
                containerColor = Color.Black,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                TiendaPoderes(
                    juegos = listOf("¿Quien era?", "Adivina el personaje", "Cartas"),
                    poderesPorJuego = poderesPorJuego,
                    gemas = gemas,
                    viewModeluser = viewModeluser,
                    juegoInicialSeleccionado = "¿Quien era?"
                )
            }
        }
    }
    if (mostrarDialogoSalir) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoSalir = false },
            title = {
                Text(
                    text = "¿Deseas salir del juego?",
                    fontFamily = FontFamily(
                        Font(R.font.fuente_luckiest)
                    ),
                )
            },
            text = {
                Text(
                    text = "Si sales ahora, perderás el progreso de esta partida.",
                    fontFamily = FontFamily(
                        Font(R.font.fuente_luckiest)
                    ),
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialogoSalir = false
                    // Aquí haces que el juego se termine: poner vidas en 0
                    viewModel.TerminarJuego()
                }) {
                    Text("Salir")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoSalir = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}