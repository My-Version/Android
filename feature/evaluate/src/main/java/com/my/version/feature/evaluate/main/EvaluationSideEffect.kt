package com.my.version.feature.evaluate.main

import androidx.annotation.StringRes

sealed class EvaluationSideEffect {
    data class ShowToast(@StringRes val message: Int) : EvaluationSideEffect()
}