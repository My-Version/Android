package com.my.version.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyVersionBasicTopAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: List<@Composable () -> Unit> = emptyList(),
    title: String = ""
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            navigationIconContentColor = White,
            titleContentColor = White,
            containerColor = MyVersionBackground
        ),
        actions = { actions.forEach { it() } },
        modifier = modifier.padding(horizontal = 12.dp)
    )
}




