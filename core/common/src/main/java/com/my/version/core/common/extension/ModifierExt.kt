package com.my.version.core.common.extension

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier

fun Modifier.noScaffoldPadding() = this
    .statusBarsPadding()
    .navigationBarsPadding()
    .systemBarsPadding()