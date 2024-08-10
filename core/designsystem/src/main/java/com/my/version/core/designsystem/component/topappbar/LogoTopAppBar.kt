package com.my.version.core.designsystem.component.topappbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.my.version.core.designsystem.theme.MyVersionTheme

@Composable
fun LogoTopAppBar(
    modifier: Modifier = Modifier
) {
    MyVersionTopAppBar(
        navigationIcon = {

        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun LogoTopAppBarPreview() {
    LogoTopAppBar()
}