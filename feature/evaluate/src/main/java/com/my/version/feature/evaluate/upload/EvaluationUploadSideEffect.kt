package com.my.version.feature.evaluate.upload

import androidx.annotation.StringRes

sealed class EvaluationUploadSideEffect {
    data object CompleteSeekTo : EvaluationUploadSideEffect()
}