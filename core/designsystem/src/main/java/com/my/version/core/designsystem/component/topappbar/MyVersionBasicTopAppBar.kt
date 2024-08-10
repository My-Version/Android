package com.my.version.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyVersionBasicTopAppBar(
    navigationIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
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
        modifier = modifier.padding(horizontal = 12.dp)
    )
}




