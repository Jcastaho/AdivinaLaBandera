package com.straccion.adivinalabandera.domain.repository

import com.straccion.adivinalabandera.domain.models.DatosUser
import com.straccion.adivinalabandera.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface QuienEsDifNormalRepository {
    suspend fun guardarNuevoUser(datosUser: DatosUser): Response<Boolean>
    suspend fun actualizarScoresUser(
        idUser: String, score: Int, dificultad: Int, nombreJuego: String): Response<Boolean>
    suspend fun obtenerGemasUser(idUser: String): Flow<Response<Int>>
    suspend fun actualizarGemas(idUser: String, gemas: Int): Response<Boolean>
    suspend fun obtenerDatosUser(): Flow<Response<List<DatosUser>>>
    suspend fun obtenerDatosUserPorId(idUser: String): Flow<Response<DatosUser?>>

    //////
    suspend fun actualizarPoderes(
        idUser: String, numeroJuego: String, poder: String, cantidadASumar: Int): Response<Boolean>
    suspend fun obtenerPoderesJuego1(idUser: String): Flow<Response<Map<String, Int>>>
    suspend fun obtenerPoderesJuego2(idUser: String): Flow<Response<Map<String, Int>>>

}