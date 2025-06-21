package com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal

import com.straccion.adivinalabandera.domain.repository.QuienEsDifNormalRepository
import javax.inject.Inject

class ActualizarPoderes @Inject constructor(private val repository: QuienEsDifNormalRepository) {
    suspend operator fun invoke(
        idUser: String, numeroJuego: String,
        poder: String, cantidadASumar: Int
    ) =
        repository.actualizarPoderes(idUser, numeroJuego, poder, cantidadASumar)
}