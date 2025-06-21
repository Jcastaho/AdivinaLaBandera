package com.straccion.adivinalabandera.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultFloatingActionButton(
    menuAbierto: Boolean,
    onClickMostrarBolsa: () -> Unit,
    onClickMostrarTienda: () -> Unit,
    onClickMostrarMenu: () -> Unit,
) {
    Box(
        modifier = Modifier.padding(bottom = 10.dp, end = 10.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Botón de Bolsa
        AnimatedVisibility(
            visible = menuAbierto,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 120.dp), // posición relativa
                containerColor = Color(0xFF00897B),
                onClick = {
                    onClickMostrarBolsa()
                }
            ) {
                Icon(
                    Icons.Default.Backpack,
                    contentDescription = "Inventario",
                    tint = Color.White
                )
            }
        }

        // Botón de Tienda
        AnimatedVisibility(
            visible = menuAbierto,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 60.dp),
                containerColor = Color(0xFF6A1B9A),
                onClick = {
                    onClickMostrarTienda()
                }
            ) {
                Icon(
                    Icons.Default.Store,
                    contentDescription = "Tienda",
                    tint = Color.White
                )
            }
        }

        // Botón principal (Abre/Cierra menú)
        FloatingActionButton(
            modifier = Modifier.size(56.dp),
            containerColor = MaterialTheme.colorScheme.onSurface,
            onClick = {
                onClickMostrarMenu()
            }
        ) {
            Icon(
                imageVector = if (menuAbierto) Icons.Default.Close else Icons.Default.HealthAndSafety,
                contentDescription = "Menú",
                tint = MaterialTheme.colorScheme.surface
            )
        }
    }
}
