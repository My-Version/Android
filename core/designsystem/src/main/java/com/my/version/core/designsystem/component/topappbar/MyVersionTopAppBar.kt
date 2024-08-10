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
fun MyVersionTopAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes title: Int? = null
) {
    TopAppBar(
        title = {
            Text(
                text = if(title == null) "" else stringResource(id = title),
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(id = R.string.top_app_bar_navigate_up),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .noRippleClickable { navigateUp() }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyVersionTopAppBarPreview() {
    MyVersionTheme {
        MyVersionTopAppBar(
            title = R.string.preview_title,
            canNavigateBack = true,
            navigateUp = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyVersionTopAppBarEmptyPreview() {
    MyVersionTheme {
        MyVersionTopAppBar(
            canNavigateBack = false,
            navigateUp = {}
        )
    }
}


