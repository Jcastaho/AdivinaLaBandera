package com.straccion.adivinalabandera.presentation.components

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.util.Locale
import com.straccion.adivinalabandera.R

//saber si tiene internet
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

//lenguaje
fun Context.updateLocale(languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        createConfigurationContext(config)
    } else {
        resources.updateConfiguration(config, resources.displayMetrics)
        this
    }
}

@Composable
fun TextoConEfectoLava(
    texto: String,
    font: Int = 15,
    fontWeight: FontWeight = FontWeight.Bold
) {
    val infiniteTransition = rememberInfiniteTransition()

    val animatedOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = EaseIn)
        )
    )

    val coloresLava = listOf(
        Color(0xFFFF0000), // rojo brillante
        Color(0xFFFF0000), // rojo brillante
        Color(0xFFFFD700), // amarillo brillante
        Color(0xFFFF4500), // rojo anaranjado
        Color(0xFFFFA500), // naranja
        Color(0xFFFF0000), // rojo brillante
    )

    val brush = Brush.linearGradient(
        colors = coloresLava,
        start = Offset(0f, 0f),
        end = Offset(x = animatedOffset.value, y = animatedOffset.value)
    )

    Text(
        text = texto,
        fontSize = font.sp,
        fontWeight = fontWeight,
        fontFamily = FontFamily(Font(R.font.fuente_luckiest)),
        style = TextStyle(brush = brush) // <- usamos el degradado animado
    )
}
