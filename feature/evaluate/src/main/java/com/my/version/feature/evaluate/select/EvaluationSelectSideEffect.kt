package com.my.version.feature.evaluate.select

import android.net.Uri
import androidx.annotation.StringRes
import com.my.version.core.domain.entity.CoverAudio

sealed class EvaluationSelectSideEffect {
    data class ShowToast(@StringRes val message: Int) : EvaluationSelectSideEffect()
    data object NavigateUp : EvaluationSelectSideEffect()
    data class NavigateNext(val cover: CoverAudio) : EvaluationSelectSideEffect()
    data class StartCoverAudio(val uri: Uri) : EvaluationSelectSideEffect()
    data object PlayCoverAudio : EvaluationSelectSideEffect()
    data object PauseCoverAudio : EvaluationSelectSideEffect()
}