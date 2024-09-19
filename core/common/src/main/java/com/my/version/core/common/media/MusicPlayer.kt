package com.my.version.core.common.media

import android.media.MediaPlayer
import androidx.compose.runtime.mutableIntStateOf
import java.io.File

object MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused = false

    private var _audioLength = 0
    val audioLength get() = _audioLength

    fun playMusic(
        file: File?,
        setOnCompletion: () -> Unit = { mediaPlayer?.reset() },
    ) {
        if (mediaPlayer?.isPlaying == true) {
            stopMusic()
        }
        file?.let {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(it.absolutePath)
                prepare()
                setOnPreparedListener {
                    _audioLength = it.duration
                }
                setOnCompletionListener { setOnCompletion() }
            }
        }
        try {
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false

    fun pauseMusic() {
        if (!isPaused && mediaPlayer != null) {
            mediaPlayer?.pause()
            isPaused = true
        }
    }

    fun resumeAudio() {
        if (isPaused && mediaPlayer != null) {
            mediaPlayer?.start()
            isPaused = false
        }
    }

    fun stopMusic() {
        if (mediaPlayer != null || isPaused) {
            mediaPlayer?.run {
                stop()
                reset()
                release()
            }
            mediaPlayer = null
            isPaused = false
        }
    }
}