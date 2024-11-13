package com.my.version.feature.evaluate.main

import androidx.annotation.StringRes
import com.my.version.core.domain.entity.EvaluationDetail

sealed class EvaluationSideEffect {
    data class ShowToast(@StringRes val message: Int) : EvaluationSideEffect()
    data class NavigateToResult(val result: EvaluationDetail) : EvaluationSideEffect()
    data object NavigateToSelect : EvaluationSideEffect()
}