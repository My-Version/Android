package com.my.version.feature.cover.select

import android.net.Uri
import androidx.annotation.StringRes
import com.my.version.core.domain.entity.MusicAudio

sealed class CoverSelectSideEffect {
    data class ShowToast(@StringRes val message: Int) : CoverSelectSideEffect()
    data object NavigateUp : CoverSelectSideEffect()
    data class NavigateNext(val music: MusicAudio) : CoverSelectSideEffect()
    data class StartMusic(val uri: Uri) : CoverSelectSideEffect()
    data object PlayMusic : CoverSelectSideEffect()
    data object PauseMusic : CoverSelectSideEffect()
}