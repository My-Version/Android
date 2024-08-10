package com.my.version.core.designsystem.component.topappbar

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.R

@Composable
fun NavigateUpTopAppBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
) {
    MyVersionTopAppBar(
        title = title,
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = stringResource(id = R.string.top_app_bar_navigate_up),
                modifier = Modifier.noRippleClickable { onNavigateUp() }
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun NavigateUpTopAppBarPreview() {
    NavigateUpTopAppBar(
        title = stringResource(id = R.string.preview_title),
        onNavigateUp = {}
    )
}