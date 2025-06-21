package com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal

import com.straccion.adivinalabandera.domain.repository.QuienEsDifNormalRepository
import javax.inject.Inject


class ObtenerDatosUserPorId @Inject constructor(private val repository: QuienEsDifNormalRepository) {
    suspend operator fun invoke(idUser: String) = repository.obtenerDatosUserPorId(idUser)
}