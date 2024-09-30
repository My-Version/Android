package com.my.version.core.designsystem.component.topappbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.White

@Composable
fun LogoTopAppBar(
    modifier: Modifier = Modifier,
    actions: List<@Composable () -> Unit> = emptyList()
) {
    MyVersionBasicTopAppBar(
        modifier = modifier,
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo_top_bar),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
            )
        },
        actions = actions
    )
}

@Preview(showBackground = true)
@Composable
fun LogoTopAppBarPreview() {
    LogoTopAppBar(
        modifier = Modifier.background(color = MyVersionBackground)
    )
}