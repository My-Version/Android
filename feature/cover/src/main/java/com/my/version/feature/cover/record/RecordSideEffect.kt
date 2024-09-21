package com.my.version.feature.cover.record

sealed class RecordSideEffect {
    data object NavigateUp: RecordSideEffect()
    data object StopRecord: RecordSideEffect()
}