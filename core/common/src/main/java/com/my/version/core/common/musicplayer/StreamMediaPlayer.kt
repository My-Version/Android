package com.my.version.core.common.musicplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class StreamMediaPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var mediaPlayer: MediaPlayer? = null

    fun prepareMediaPlayer(
        uri: Uri,
        onPrepared: () -> Unit = {},
        onCompletion: () -> Unit = {},
        onBufferingComplete: () -> Unit = {}
    ) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(context, uri)
                    prepareAsync()
                    setOnPreparedListener {
                        onPrepared()
                    }
                    setOnBufferingUpdateListener { _, percent ->
                        if (percent == 100) {
                            onBufferingComplete()
                        }
                    }
                    setOnCompletionListener {
                        onCompletion()
                    }
                }
            }
        } catch (e: Exception) {
            Timber.tag("StreamMediaPlayer").d(e.toString())
        }
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
        mediaPlayer?.seekTo(target)
        val progress = mediaPlayer?.currentPosition?.toFloat() ?: 0f
        Timber.tag("StreamMediaPlayer").d("progress: $progress")
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

    fun resetMediaPlayer() {
        mediaPlayer?.run {
            try {
                reset()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getMediaPlayerDuration(): Int = mediaPlayer?.duration ?: 0
    fun getMediaPlayerProgress(): Float {
        val progress = mediaPlayer?.currentPosition?.toFloat() ?: 0f
        val duration = mediaPlayer?.duration?.toFloat() ?: 0f
        return if (duration != 0f) progress / duration
        else 0f
    }
}