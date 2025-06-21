package com.straccion.adivinalabandera.presentation.screens.dificultad

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class PantallaElegirDificultadViewModel(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val juego: String = checkNotNull(savedStateHandle["juego"])

    val rutaJuego = juego

}