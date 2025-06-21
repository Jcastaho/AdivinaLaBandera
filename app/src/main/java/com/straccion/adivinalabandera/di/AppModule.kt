package com.straccion.adivinalabandera.di

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.straccion.adivinalabandera.core.Constants.QUIEN_ES_NORMAL
import com.straccion.adivinalabandera.data.repository.QuienEsDifNormalImp
import com.straccion.adivinalabandera.domain.repository.QuienEsDifNormalRepository
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ActualizarGemasUser
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ActualizarPoderes
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ActualizarScoresUser
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.GuardarNuevoUser
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ObtenerDatosUseCases
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ObtenerDatosUser
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ObtenerDatosUserPorId
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ObtenerGemasUser
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ObtenerPoderesJuego1
import com.straccion.adivinalabandera.domain.use_cases.quienesdif_normal.ObtenerPoderesJuego2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }


    @Provides
    fun providesFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun providerQuienEsDifNormalImp(impl: QuienEsDifNormalImp): QuienEsDifNormalRepository = impl

    @Provides
    @Named(QUIEN_ES_NORMAL)
    fun providesUsersRef(db: FirebaseFirestore): CollectionReference =
        db.collection(QUIEN_ES_NORMAL)

    @Provides
    fun providerObtenerScoresQuienEsDifNormalUseCase(repository: QuienEsDifNormalRepository) =
        ObtenerDatosUseCases(
            allDatosUser = ObtenerDatosUser(repository),
            obtenerDatosUserPorId = ObtenerDatosUserPorId(repository),
            actualizarScoresUser = ActualizarScoresUser(repository),
            guardarNuevoUser = GuardarNuevoUser(repository),
            actualizarGemasUser = ActualizarGemasUser(repository),
            obtenerGemasUser = ObtenerGemasUser(repository),
            actualizarPoderes = ActualizarPoderes(repository),
            obtenerPoderesJuego1 = ObtenerPoderesJuego1(repository),
            obtenerPoderesJuego2 = ObtenerPoderesJuego2(repository)
        )
}