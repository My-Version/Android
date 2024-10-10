package com.my.version.core.common.musicplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import timber.log.Timber

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

    fun endMediaPlayer() {
        Timber.tag("StreamMusic").d(mediaPlayer.toString())
        mediaPlayer?.run {
            try {
                stop()
                release()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                mediaPlayer = null
                Timber.tag("StreamMusic").d(mediaPlayer.toString())
            }
        }
    }
}