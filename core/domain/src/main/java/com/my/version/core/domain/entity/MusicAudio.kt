package com.my.version.core.domain.entity

/**
 * @param title 노래 제목
 * @param artist 가수 이름
 * @param audio 서버 상 노래 파일 이름 [노래-가수.mp3]
 */

data class MusicAudio(
    val title: String = "",
    val artist: String = "",
    val audio: String = ""
)
