package com.straccion.adivinalabandera.presentation

import android.app.Application
import androidx.annotation.RawRes
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ViewModelMusic @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    //--------------------Musica---------------------

    private var exoPlayer: ExoPlayer? = null


    private val _isPlay = MutableStateFlow(false)
    val isMute: StateFlow<Boolean> = _isPlay


    private val _verConfig = MutableStateFlow(false)
    val verConfig: StateFlow<Boolean> = _verConfig

    fun initializePlayer(@RawRes rawResId: Int) {
        if (exoPlayer != null) return // Ya está iniciado

        val context = getApplication<Application>()
        exoPlayer?.release() // Por si acaso

        exoPlayer = ExoPlayer.Builder(context)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setHandleAudioBecomingNoisy(true)
            .build()
            .apply {
                val mediaItem = MediaItem.fromUri(
                    "android.resource://${context.packageName}/$rawResId"
                )
                setMediaItem(mediaItem)
                repeatMode = ExoPlayer.REPEAT_MODE_ALL
                prepare()
                playWhenReady = true
            }
        _isPlay.value = true
    }


    fun play() {
        if (_isPlay.value) return
        exoPlayer?.play()
        _isPlay.value = true
    }

    fun pause() {
        exoPlayer?.pause()
        _isPlay.value = false
    }

    fun changeMusic(@RawRes rawResId: Int) {
        if (_isPlay.value) {
            val context = getApplication<Application>().applicationContext
            val mediaUri = "android.resource://${context.packageName}/$rawResId"
            val mediaItem = MediaItem.Builder()
                .setUri(mediaUri)
                .build()
            exoPlayer?.apply {
                stop()
                setMediaItem(mediaItem)
                prepare()
                play()
            }
            _isPlay.value = true
        }
    }

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
        _isPlay.value = false
    }

    //Config
    fun MostrarConfig() {
        _verConfig.value = true
    }

    fun OcultarConfig() {
        _verConfig.value = false
    }


}