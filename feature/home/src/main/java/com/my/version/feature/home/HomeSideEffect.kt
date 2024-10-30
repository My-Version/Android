package com.my.version.feature.home

import android.net.Uri
import androidx.annotation.StringRes

sealed class HomeSideEffect {
    data class ShowToast(@StringRes val message: Int) : HomeSideEffect()
    data class StartMusic(val uri: Uri) : HomeSideEffect()
    data object PlayMusic : HomeSideEffect()
    data object PauseMusic : HomeSideEffect()
    data object StopMusic : HomeSideEffect()
}