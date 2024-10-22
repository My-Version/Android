package com.my.version.core.common.musicplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class StreamMediaPlayer(
    private val context: Context
) {
    private var mediaPlayer: MediaPlayer? = null

    fun prepareMediaPlayer(uri: Uri) = runCatching {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(context, uri)
                prepareAsync()
                setOnPreparedListener {
                    it.start()
                }
            }
        }
    }.onSuccess {
        playMediaPlayer()
    }

    fun playMediaPlayer() {
        mediaPlayer?.run {
            start()
        }
    }

    fun pauseMediaPlayer() {
        mediaPlayer?.run {
            if (isPlaying) {
                pause()
            }
        }
    }

    fun seekInMediaPlayer(target: Int) {
        mediaPlayer?.run {
            seekTo(target)
        }
    }

    fun endMediaPlayer() {
        mediaPlayer?.run {
            try {
                stop()
                release()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mediaPlayer = null
            }
        }
    }
}