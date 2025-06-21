package com.straccion.adivinalabandera

import android.app.Application
import com.google.android.gms.games.PlayGamesSdk
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AdivinaLaBandera : Application()  {
    override fun onCreate() {
        super.onCreate()
        // Aquí inicializas Play Games
        PlayGamesSdk.initialize(this)
        FirebaseApp.initializeApp(this)
    }
}