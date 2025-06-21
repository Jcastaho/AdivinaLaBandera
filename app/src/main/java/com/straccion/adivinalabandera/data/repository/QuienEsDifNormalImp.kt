package com.straccion.adivinalabandera.data.repository


import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.straccion.adivinalabandera.core.Constants.QUIEN_ES_NORMAL
import com.straccion.adivinalabandera.domain.models.AyudasPoderes
import com.straccion.adivinalabandera.domain.models.DatosUser
import com.straccion.adivinalabandera.domain.models.Response
import com.straccion.adivinalabandera.domain.repository.QuienEsDifNormalRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class QuienEsDifNormalImp @Inject constructor(
    @Named(QUIEN_ES_NORMAL) private val quienEsDifNormal: CollectionReference
) : QuienEsDifNormalRepository {
    private val TAG = "ViewModelGestionarUser"
    override suspend fun guardarNuevoUser(datosUser: DatosUser): Response<Boolean> {
        return try {
            // Busca el documento donde el campo "idUser" es igual al id proporcionado
            val document = quienEsDifNormal.whereEqualTo("idUser", datosUser.idUser).get().await()
            if (!document.isEmpty) {
                Response.Success(true)
            } else {
                /////////////
                val poderesJuego1 =
                    obtenerPoderesIniciales(AyudasPoderes.AyudasPoderes.listaPoderesJuego1)
                val poderesJuego2 =
                    obtenerPoderesIniciales(AyudasPoderes.AyudasPoderes.listaPoderesJuego2)
                /////////////
                val data = hashMapOf(
                    "idUser" to datosUser.idUser,
                    "nombre" to datosUser.nombre,
                    "avatar" to datosUser.avatar,
                    "monedas" to hashMapOf(
                        "juego1" to hashMapOf(
                            "gemas" to 0,
                        )
                    ),
                    "tipoJuegos" to hashMapOf(
                        "juego1" to hashMapOf(
                            "normal" to 0,
                            "dificil" to 0
                        )
                    ),
                    /////////////
                    "poderes" to hashMapOf(
                        "¿Quien era?" to poderesJuego1,
                        "Adivina el personaje" to poderesJuego2
                    )
                )
                quienEsDifNormal.add(data).await()
                Response.Success(true)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun obtenerPoderesJuego1(idUser: String): Flow<Response<Map<String, Int>>> =
        callbackFlow {
            val documentListener = quienEsDifNormal
                .whereEqualTo("idUser", idUser)
                .addSnapshotListener { snapshot, e ->
                    val poderesResponse = if (snapshot != null) {
                        try {
                            val poderes = mutableMapOf<String, Int>()
                            for (doc in snapshot.documents) {
                                val poderesMap = doc.get("poderes.¿Quien era?") as? Map<*, *>
                                poderesMap?.forEach { (key, value) ->
                                    poderes[key.toString()] = (value as? Long)?.toInt() ?: 0
                                }
                            }
                            Response.Success(poderes)
                        } catch (e: Exception) {
                            Response.Failure(e)
                        }
                    } else {
                        Response.Failure(e ?: Exception("Error desconocido"))
                    }
                    trySend(poderesResponse)
                }
            awaitClose {
                documentListener.remove()
            }
        }

    override suspend fun obtenerPoderesJuego2(idUser: String): Flow<Response<Map<String, Int>>> =
        callbackFlow {
            val documentListener = quienEsDifNormal
                .whereEqualTo("idUser", idUser)
                .addSnapshotListener { snapshot, e ->
                    val poderesResponse = if (snapshot != null) {
                        try {
                            val poderes = mutableMapOf<String, Int>()
                            for (doc in snapshot.documents) {
                                val poderesMap = doc.get("poderes.Adivina el personaje") as? Map<*, *>
                                poderesMap?.forEach { (key, value) ->
                                    poderes[key.toString()] = (value as? Long)?.toInt() ?: 0
                                }
                            }
                            Response.Success(poderes)
                        } catch (e: Exception) {
                            Response.Failure(e)
                        }
                    } else {
                        Response.Failure(e ?: Exception("Error desconocido"))
                    }
                    trySend(poderesResponse)
                }
            awaitClose {
                documentListener.remove()
            }
        }
    /////////////


    override suspend fun obtenerGemasUser(idUser: String): Flow<Response<Int>> = callbackFlow {
        val documentListener = quienEsDifNormal
            .whereEqualTo("idUser", idUser)
            .addSnapshotListener { snapshot, e ->
                val gemasResponse = if (snapshot != null) {
                    try {
                        var gemas: Int = 0
                        for (doc in snapshot.documents) {
                            gemas = doc.getLong("monedas.juego1.gemas")?.toInt()
                                ?: break // Asumimos que solo habrá un documento por idUser
                        }
                        Response.Success(gemas)
                    } catch (e: Exception) {
                        Response.Failure(e)
                    }
                } else {
                    Response.Failure(e ?: Exception("Unknown error occurred"))
                }
                trySend(gemasResponse)
            }
        awaitClose {
            documentListener.remove()
        }
    }

    override suspend fun actualizarScoresUser(
        idUser: String,
        score: Int,
        dificultad: Int,
        nombreJuego: String
    ): Response<Boolean> {
        return try {
            val document = quienEsDifNormal.whereEqualTo("idUser", idUser).get().await()
            if (!document.isEmpty) {
                for (doc in document.documents) {
                    val updateData = mutableMapOf<String, Any>()
                    val juegoPath = "tipoJuegos.$nombreJuego"
                    val juegoExiste = doc.get("tipoJuegos.$nombreJuego") != null
                    if (!juegoExiste) {                 // Crear estructura completa del juego si no existe
                        updateData[juegoPath] = hashMapOf(
                            "normal" to if (dificultad == 0) score else 0,
                            "dificil" to if (dificultad == 1) score else 0
                        )
                    } else {     // Actualizar solo la dificultad especificada
                        val campoDificultad = if (dificultad == 0) "normal" else "dificil"
                        updateData["$juegoPath.$campoDificultad"] = score
                    }

                    if (updateData.isNotEmpty()) {
                        quienEsDifNormal.document(doc.id).update(updateData).await()
                    }
                }
                Response.Success(true)
            } else {
                Response.Success(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun actualizarGemas(
        idUser: String,
        gemas: Int
    ): Response<Boolean> {
        return try {
            val document = quienEsDifNormal.whereEqualTo("idUser", idUser).get().await()
            if (!document.isEmpty) {
                for (doc in document.documents) {
                    val updateData = mutableMapOf<String, Any>()
                    val juegoPath = "monedas.juego1"
                    val juegoExiste = doc.get("monedas.juego1") != null

                    if (!juegoExiste) {// Crear estructura completa del juego si no existe
                        updateData[juegoPath] = hashMapOf(
                            "gemas" to gemas
                        )
                    } else {
                        updateData[juegoPath] = hashMapOf(
                            "gemas" to gemas
                        )
                    }
                    if (updateData.isNotEmpty()) {
                        quienEsDifNormal.document(doc.id).update(updateData).await()
                    }
                }
                Response.Success(true)
            } else {
                Log.d(TAG, "No se encontraron documentos para el idUser: $idUser.")
                Response.Success(false)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Ocurrió una excepción al actualizar las gemas: ${e.message}")
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun actualizarPoderes(
        idUser: String,
        numeroJuego: String,
        poder: String,
        cantidadASumar: Int
    ): Response<Boolean> {
        return try {
            val document = quienEsDifNormal.whereEqualTo("idUser", idUser).get().await()
            if (!document.isEmpty) {
                for (doc in document.documents) {
                    val updateData = mutableMapOf<String, Any>()

                    val poderesJuego1 = obtenerPoderesIniciales(AyudasPoderes.AyudasPoderes.listaPoderesJuego1)
                    val poderesJuego2 = obtenerPoderesIniciales(AyudasPoderes.AyudasPoderes.listaPoderesJuego2)

                    val juegoPath = "poderes"

                    val poderes = doc.get(juegoPath) as? Map<*, *>

                    if (poderes == null) {
                        // No existe la estructura, crearla completamente
                        // Primero seteamos todos los poderes en cero
                        val nuevosPoderes = hashMapOf(
                            "¿Quien era?" to poderesJuego1.toMutableMap(),
                            "Adivina el personaje" to poderesJuego2.toMutableMap()
                        )
                        // Luego actualizamos el poder específico con la cantidadASumar
                        (nuevosPoderes[numeroJuego] as MutableMap<String, Int>)[poder] = cantidadASumar

                        updateData[juegoPath] = nuevosPoderes
                    } else {
                        // Ya existe la estructura: sumamos el poder correspondiente
                        val poderPath = "$juegoPath.$numeroJuego.$poder"
                        val poderActual = doc.get(poderPath) as? Long ?: 0L
                        updateData[poderPath] = (poderActual + cantidadASumar)
                    }

                    if (updateData.isNotEmpty()) {
                        quienEsDifNormal.document(doc.id).update(updateData).await()
                    }
                }
                Response.Success(true)
            } else {
                Response.Success(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun obtenerDatosUserPorId(userId: String): Flow<Response<DatosUser?>> =
        callbackFlow {
            val snapshotListener = quienEsDifNormal
                .whereEqualTo("idUser", userId)
                .addSnapshotListener { snapshot, e ->
                    val userResponse = if (snapshot != null) {
                        try {
                            // Obtenemos el primer documento encontrado (si existe)
                            val user = snapshot.documents.firstOrNull()?.let { doc ->
                                doc.toObject(DatosUser::class.java)?.apply {
                                    idUser = doc.getString("idUser") ?: doc.id
                                }
                            }
                            // Si no se encuentra un usuario, el valor será null
                            Response.Success(user)
                        } catch (e: Exception) {
                            Response.Failure(e)
                        }
                    } else {
                        Response.Failure(e ?: Exception("Unknown error occurred"))
                    }
                    trySend(userResponse as Response<DatosUser>)
                }
            awaitClose {
                snapshotListener.remove()
            }
        }

    override suspend fun obtenerDatosUser(): Flow<Response<List<DatosUser>>> =
        callbackFlow {
            val snapshotListener = quienEsDifNormal
                .addSnapshotListener { snapshot, e ->
                    val userResponse = if (snapshot != null) {
                        try {
                            val user = snapshot.documents.mapNotNull { doc ->
                                doc.toObject(DatosUser::class.java)?.apply {
                                    idUser = doc.getString("idUser") ?: doc.id
                                }
                            }
                            Response.Success(user)
                        } catch (e: Exception) {
                            Response.Failure(e)
                        }
                    } else {
                        Response.Failure(e ?: Exception("Unknown error occurred"))
                    }
                    trySend(userResponse)
                }
            awaitClose {
                snapshotListener.remove()
            }
        }

    /////////////
    fun obtenerPoderesIniciales(listaPoderes: List<AyudasPoderes>): HashMap<String, Int> {
        return hashMapOf<String, Int>().apply {
            listaPoderes.forEach { poder ->
                put(poder.poderId, 0)
            }
        }
    }
/////////////
/////////////

}
