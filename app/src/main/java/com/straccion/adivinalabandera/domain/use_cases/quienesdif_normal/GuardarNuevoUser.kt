package com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal

import com.straccion.adivinalabandera.domain.models.DatosUser
import com.straccion.adivinalabandera.domain.repository.QuienEsDifNormalRepository
import javax.inject.Inject

class GuardarNuevoUser @Inject constructor(private val repository: QuienEsDifNormalRepository) {
    suspend operator fun invoke(datosUser: DatosUser) = repository.guardarNuevoUser(datosUser)
}