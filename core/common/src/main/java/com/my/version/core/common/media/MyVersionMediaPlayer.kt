package com.my.version.core.common.media

import android.media.MediaPlayer
import java.io.File

object MyVersionMediaPlayer{
    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer()
    }

    fun setMusic(file: File?) {
        file?.let {
            with(mediaPlayer) {
                setDataSource(it.absolutePath)
                prepare()
                setOnCompletionListener {
                    reset()
                }
            }
        }
    }

    fun playMusic() {
        mediaPlayer.start()

    }

    fun pauseMusic() {
        mediaPlayer.pause()
    }

    fun stopMusic() {
        if(mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()
    }

    fun releaseMediaPlayer() {
        mediaPlayer.release()
    }
}