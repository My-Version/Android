package com.my.version.feature.evaluate.upload

sealed class EvaluationUploadSideEffect {
    data object CompleteSeekTo : EvaluationUploadSideEffect()
}