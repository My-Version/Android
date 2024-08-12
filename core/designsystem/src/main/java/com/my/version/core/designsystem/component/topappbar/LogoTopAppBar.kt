package com.my.version.core.designsystem.component.topappbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LogoTopAppBar(
    modifier: Modifier = Modifier,
    actions: List<@Composable () -> Unit> = emptyList()
) {
    MyVersionBasicTopAppBar(
        navigationIcon = {},
        actions = actions,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun LogoTopAppBarPreview() {
    LogoTopAppBar()
}