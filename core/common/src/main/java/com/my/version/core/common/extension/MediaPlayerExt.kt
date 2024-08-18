package com.my.version.core.common.extension

import android.media.MediaPlayer

fun MediaPlayer.setNewPlayer(filePath: String): MediaPlayer {
    setDataSource(filePath)
    this.prepare()
    this.start()
    return this
}

fun MediaPlayer.stopPreviousMusic() {
    if(this.isPlaying) {
        this.stop()
    }
    this.release()
}