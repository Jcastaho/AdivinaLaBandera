package com.straccion.adivinalabandera.presentation.popups.tiendapopup.tiendagemas

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ViewModelTienda @Inject constructor(
    application: Application,

    ) : AndroidViewModel(application) {
    //addmob
    private val _rewardedAd = mutableStateOf<RewardedAd?>(null)
    val rewardedAd: State<RewardedAd?> = _rewardedAd
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    // Evento para saber si el usuario ganó la recompensa
    private val _recompensaRecibidaTienda = MutableStateFlow(false)
    val recompensaRecibidaTienda: StateFlow<Boolean> = _recompensaRecibidaTienda

    private val _anuncioDisponible = MutableStateFlow(true)
    val anuncioDisponible: StateFlow<Boolean> = _anuncioDisponible
    //addmob



    //addmob
    fun cargarAnuncioRecompensado() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            "ca-app-pub-3940256099942544/5224354917", // ID de prueba
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    _rewardedAd.value = ad
                    Log.d("AdMob", "Anuncio recompensado cargado")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.e("AdMob", "Error al cargar anuncio recompensado: ${error.message}")
                    _rewardedAd.value = null
                }
            }
        )
    }

    fun mostrarAnuncio(activity: Activity) {
        rewardedAd.value?.show(activity) { rewardItem ->
            // 👇 Aquí se da la recompensa
            Log.d("AdMob", "Usuario ganó ${rewardItem.amount} ${rewardItem.type}")
            _recompensaRecibidaTienda.value = true
        } ?: Log.d("AdMob", "El anuncio aún no está listo")
    }

}