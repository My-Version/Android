package com.my.version.feature.cover.select

sealed class CoverSelectSideEffect {
    data object NavigateUp: CoverSelectSideEffect()
    data object NavigateNext: CoverSelectSideEffect()
}