package com.straccion.adivinalabandera.presentation.popups.regalospopup

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ViewModelRegalos @Inject constructor(
    application: Application,
    @ApplicationContext private val appContext: Context
) : AndroidViewModel(application) {
    //addmob
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext


    private val sharedPreferences = appContext.getSharedPreferences("regalos_prefs", Context.MODE_PRIVATE)
    private val ultimoClaimKey = "ultimo_claim_time"

    private val _puedeClaimRegalo = MutableStateFlow(puedeClaimRegaloAhora())
    val puedeClaimRegalo: StateFlow<Boolean> = _puedeClaimRegalo

    init {
        // Carga el valor inicial al iniciar el ViewModel
        _puedeClaimRegalo.value = puedeClaimRegaloAhora()
    }


    // Evento para saber si el usuario ganó la recompensa
    private val _recompensaRecibidaAnuncio = MutableStateFlow(false)
    val recompensaRecibidaAnuncio: StateFlow<Boolean> = _recompensaRecibidaAnuncio

    val recompensaRecibidaNormal = MutableStateFlow(false)

    var rewardedInterstitialAd: RewardedInterstitialAd? = null
    //addmob


    //addmob
    fun CargarAnuncioRecompensado() {
        val adRequest = AdRequest.Builder().build()
        RewardedInterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/5354046379", // ID de prueba de intersticial recompensado
            adRequest,
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    rewardedInterstitialAd = ad
                    Log.d("AdMob", "Intersticial recompensado cargado")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.e("AdMob", "Error al cargar intersticial recompensado: ${error.message}")
                    rewardedInterstitialAd = null
                }
            }
        )
    }

    fun MostrarAnuncio(activity: Activity?) {
        rewardedInterstitialAd?.show(activity!!) { rewardItem ->
            _recompensaRecibidaAnuncio.value = true
            Log.d("AdMob", "Usuario ganó ${rewardItem.amount} ${rewardItem.type}")
        } ?: Log.d("AdMob", "El anuncio aún no está listo")
    }

    fun PuedeClaimRegalo(): Boolean {
        return puedeClaimRegaloAhora()
    }

    private fun puedeClaimRegaloAhora(): Boolean {
        val ultimoClaimTime = sharedPreferences.getLong(ultimoClaimKey, 0L)
        val currentTime = System.currentTimeMillis()
        val diferencia = currentTime - ultimoClaimTime
        val diasTranscurridos = TimeUnit.MILLISECONDS.toDays(diferencia)
        return diasTranscurridos >= 1
    }

    fun RegistrarClaimRegalo() {
        sharedPreferences.edit().putLong(ultimoClaimKey, System.currentTimeMillis()).apply()
        _puedeClaimRegalo.value = false
    }

    // Función para resetear el estado (útil para pruebas o si necesitas una lógica de reseteo)
    fun ResetearEstadoRegalo() {
        sharedPreferences.edit().remove(ultimoClaimKey).apply()
        _puedeClaimRegalo.value = true
    }

}