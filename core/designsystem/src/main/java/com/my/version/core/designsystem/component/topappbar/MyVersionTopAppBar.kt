package com.my.version.core.designsystem.component.topappbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.theme.MyVersionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyVersionTopAppBar(
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



