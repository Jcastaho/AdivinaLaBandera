package com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal

import com.straccion.adivinalabandera.domain.models.DatosUser
import com.straccion.adivinalabandera.domain.models.Response
import com.straccion.adivinalabandera.domain.repository.QuienEsDifNormalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerDatosUser @Inject constructor(private val repository: QuienEsDifNormalRepository) {
    suspend operator fun invoke(): Flow<Response<List<DatosUser>>> = repository.obtenerDatosUser()
}