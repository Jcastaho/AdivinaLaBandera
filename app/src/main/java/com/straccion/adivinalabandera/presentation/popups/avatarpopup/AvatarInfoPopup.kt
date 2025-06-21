package com.straccion.adivinalabandera.presentation.popups.avatarpopup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import com.straccion.adivinalabandera.R
import com.straccion.adivinalabandera.presentation.ViewModelGestionarUser
import com.straccion.adivinalabandera.presentation.navigation.screen.tienda_inicio.TiendaInicio
import com.straccion.adivinalabandera.ui.theme.Rojito

@Composable
fun AvatarInfoPopup(
    onClick: (() -> Unit),
    navHostController: NavHostController,
    viewModelGestionarUser: ViewModelGestionarUser
) {
    val isEditable = viewModelGestionarUser.isEditable.collectAsState()//Editar el nombre
    var nuevoNombre = viewModelGestionarUser.nuevoNombre.collectAsState()
    val userProfile by viewModelGestionarUser.userProfile//traer los datos del user
    var gemas = viewModelGestionarUser.gemas.collectAsState()

    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onClick() } // Para cerrar al tocar fuera
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f)
                .padding(vertical = 30.dp)
                .clickable(
                    indication = null, // 🔥 esto quita el efecto gris
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    viewModelGestionarUser.OcultarEditarNombre()
                },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE0B2) // Un tono amarillo claro como fondo
            ),
            border = BorderStroke(8.dp, Rojito) // Borde naranja
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(R.string.perfil).uppercase(),
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100) // Naranja oscuro para el título
                )
                Spacer(modifier = Modifier.height(25.dp))
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    painter = painterResource(R.drawable.portada),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(35.dp)
                            .clickable(
                                indication = null, // 🔥 esto quita el efecto gris
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                viewModelGestionarUser.MostarEditarNombre()
                            },
                        painter = painterResource(R.drawable.icon_editar),
                        contentDescription = "Editar"
                    )
                    Spacer(modifier = Modifier.width(20.dp))
//                    if (isEditable.value) {
//                        OutlinedTextField(
//                            modifier = Modifier
//                                .fillMaxWidth(0.85f)
//                                .wrapContentHeight(),
//                            value = nuevoNombre.value, // Usar nuevoNombre.value para el String
//                            onValueChange = { viewModelGestionarUser.NuevoNombre(it) }, // Usar onValueChange para actualizar
//                            textStyle = TextStyle(
//                                fontSize = 12.sp,
//                                fontFamily = FontFamily(Font(R.font.fuente_luckiest))
//                            ),
//                            singleLine = true
//                        )
//                    } else {
                    Text(
                        modifier = Modifier
                            .clickable(
                                indication = null, // 🔥 esto quita el efecto gris
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                viewModelGestionarUser.MostarEditarNombre()
                            },
                        text = if (userProfile?.nombre != null) userProfile?.nombre!!.uppercase() else "invitado".uppercase(),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFFE65100)
                    )
                    //}
                }

                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(40.dp),
                        painter = painterResource(R.drawable.icon_googlegames),
                        contentDescription = "Google Games"
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = if (userProfile?.nombre != null) stringResource(R.string.conectado_google_play).uppercase() else
                            stringResource(R.string.no_conectado_google_play).uppercase(),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFFE65100)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.74f)
                        .padding(top = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .height(150.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFE0B2) // Un tono amarillo claro como fondo
                        ),
                        border = BorderStroke(2.dp, Rojito) // Borde naranja
                    ) {
                        Column(
                            modifier = Modifier
                                .width(120.dp)
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {

                            Text(
                                text = stringResource(R.string.tus_gemas).uppercase(),
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color(0xFFE65100)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Image(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(id = R.drawable.icon_gema), // Reemplaza con tu icono correcto
                                contentDescription = "Gema"
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = stringResource(R.string.gemas).uppercase() + ": ${gemas.value}",
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color(0xFFE65100)
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .height(150.dp)
                            .clickable(){
                                onClick()
                                navHostController.navigate(TiendaInicio.AbrirTienda.route)
                            },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFE0B2) // Un tono amarillo claro como fondo
                        ),
                        border = BorderStroke(2.dp, Rojito) // Borde naranja
                    ) {
                        Column(
                            modifier = Modifier
                                .width(120.dp)
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text(
                                text = stringResource(R.string.tienda_de_gemas).uppercase(),
                                fontSize = 17.sp,
                                fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                color = Color(0xFFE65100),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Image(
                                modifier = Modifier.size(45.dp),
                                painter = painterResource(id = R.drawable.icon_gema), // Reemplaza con tu icono correcto
                                contentDescription = "Gema"
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier.width(180.dp)
                        .padding(bottom = 10.dp),
                    onClick = {
                        onClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Rojito, // Rosa para el botón
                        contentColor = Color.White
                    ),
                ) {
                    Text(fontSize = 15.sp, text = stringResource(R.string.salir))
                }
            }
        }
    }
}