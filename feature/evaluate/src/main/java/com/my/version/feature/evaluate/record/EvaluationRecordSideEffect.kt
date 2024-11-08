package com.my.version.feature.evaluate.record

import androidx.annotation.StringRes

sealed class EvaluationRecordSideEffect{
    data class ShowToast(@StringRes val message: Int): EvaluationRecordSideEffect()
    data class NavigateToUpload(val recordId: String): EvaluationRecordSideEffect()
    data object NavigateUp: EvaluationRecordSideEffect()

}