package com.straccion.adivinalabandera.presentation.screens.pantalla_juego_finalizado

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class JuegoFinalizadoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val idJuego: String = savedStateHandle.get<String>("idJuego") ?: "jueguo1" // Valor por defecto
    val puntaje: Int = savedStateHandle.get<Int>("puntuacion") ?: 0
    val dificultad: Int = savedStateHandle.get<Int>("dificultad") ?: 0


    private val _mostrarPopup = MutableStateFlow(true)
    val mostrarPopup: StateFlow<Boolean> = _mostrarPopup

    private val _mostrarRanking = MutableStateFlow(false)
    val mostrarRanking: StateFlow<Boolean> = _mostrarRanking


    fun setMostrarPopup(value: Boolean) {
        _mostrarPopup.value = value
    }

    fun setMostrarRanking(value: Boolean) {
        _mostrarRanking.value = value
    }

}
