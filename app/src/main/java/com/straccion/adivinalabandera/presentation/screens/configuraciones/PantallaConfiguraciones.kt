package com.straccion.adivinalabandera.presentation.screens.configuraciones

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.ViewModelMusic
import com.straccion.adivinalabandera.ui.theme.Rojito

@Composable
fun PantallaConfiguraciones(
    viewModelMusic: ViewModelMusic,
    viewModelGestionarUser: ViewModelGestionarUser
) {
    val isMuted = viewModelMusic.isMute.collectAsState()//apagar musica
    val context = LocalContext.current
    var signInAttempted = false // Variable para controlar si ya se intentó iniciar sesión
    val activity = LocalActivity.current

    val userProfile by viewModelGestionarUser.userProfile//traer los datos del user

    // lenguaje
    var mostrarDialogo by remember { mutableStateOf(false) }
    var terminosPrivacidad by remember { mutableStateOf(false) }


    Popup(
        alignment = Alignment.TopCenter,
        onDismissRequest = { viewModelMusic.OcultarConfig() } // Para cerrar al tocar fuera
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(vertical = 20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE0B2) // Un tono amarillo claro como fondo
            ),
            border = BorderStroke(5.dp, Rojito) // Borde naranja
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 25.dp, horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.ajustes).uppercase(),
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100) // Naranja oscuro para el título
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable(
                                indication = null, // 🔥 esto quita el efecto gris
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                if (isMuted.value) {
                                    viewModelMusic.pause()
                                } else {
                                    viewModelMusic.play()
                                }
                            },
                        imageVector = if (isMuted.value) Icons.Filled.MusicNote else Icons.Filled.MusicOff,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable(
                                indication = null, // 🔥 esto quita el efecto gris
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                if (userProfile?.nombre != null) {
                                    Toast.makeText(
                                        context,
                                        "Ya estas conectado a Google Play Games",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    activity?.let {
                                        if (!signInAttempted) {
                                            // Marcar que ya se intentó iniciar sesión
                                            viewModelGestionarUser.SignIn(activity)
                                            signInAttempted = true
                                        }
                                    }
                                }
                            },
                        painter = painterResource(R.drawable.icon_googlegames),
                        contentDescription = "Google"
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.95f),
                    onClick = {
                        mostrarDialogo = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Rojito, // Rosa para el botón
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = stringResource(R.string.lenguaje).uppercase())
                }
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.95f),
                    onClick = {
                        terminosPrivacidad = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Rojito, // Rosa para el botón
                        contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.terminos_condiciones).uppercase())
                }
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.95f),
                    onClick = {
                        viewModelMusic.OcultarConfig()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Rojito, // Rosa para el botón
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = stringResource(R.string.salir).uppercase())
                }
            }
        }
    }
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text(text = stringResource(R.string.seleccionar_idioma)) },
            text = {
                Column {
                    TextButton(onClick = {
                        viewModelGestionarUser.CambiarIdioma(context, "es")
                    }) {
                        Text(text = stringResource(R.string.español))
                    }
                    TextButton(onClick = {
                        viewModelGestionarUser.CambiarIdioma(context, "en")
                    }) {
                        Text(text = stringResource(R.string.ingles))
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarDialogo = false
                }) {
                    Text("Cerrar")
                }
            },
            confirmButton = {}
        )
    }
    if (terminosPrivacidad) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text(text = "Terminos y privacidad") },
            text = {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Política de Privacidad\n\n" +
                                "Este juego utiliza tu cuenta de Google Play Games para guardar tu progreso. No recopilamos información personal ni compartimos tus datos.\n\n" +
                                "Términos de Uso\n\n" +
                                "Este juego es solo para entretenimiento. Está prohibido intentar modificar el juego o hacer trampa.\n\n" +
                                "Contacto\n\n" +
                                "Si tienes dudas, puedes contactarnos al correo: tuemail@example.com",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    terminosPrivacidad = false
                }) {
                    Text("Cerrar")
                }
            },
            confirmButton = { }
        )
    }
}