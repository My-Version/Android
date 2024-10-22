package com.my.version.feature.cover.main

import android.net.Uri

sealed class CoverSideEffect {
    data class StartCoverAudio(val uri: Uri) : CoverSideEffect()
    data object PlayCoverAudio : CoverSideEffect()
    data object PauseCoverAudio : CoverSideEffect()
}