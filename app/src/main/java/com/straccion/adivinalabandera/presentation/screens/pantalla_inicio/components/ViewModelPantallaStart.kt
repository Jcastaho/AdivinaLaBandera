package com.straccion.adivinalabandera.presentation.screens.pantalla_inicio.components

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ViewModelPantallaStart @Inject constructor(): ViewModel() {

    //region Estados de tienda y regalos
    private val _verAvatar = MutableStateFlow(false)
    val verAvatar: StateFlow<Boolean> = _verAvatar

    private val _verRegalos = MutableStateFlow(false)
    val verRegalos: StateFlow<Boolean> = _verRegalos

    private val _verTienda = MutableStateFlow(false)
    val verTienda: StateFlow<Boolean> = _verTienda

    fun MostrarTienda() {
        _verTienda.value = true
    }
    fun OcultarTienda() {
        _verTienda.value = false
    }

    fun MostrarRegalos() {
        _verRegalos.value = true
    }
    fun OcultarRegalos() {
        _verRegalos.value = false
    }
    fun MostrarAvatar() {
        _verAvatar.value = true
    }
    fun OcultarAvatar() {
        _verAvatar.value = false
    }
    //endregion
}