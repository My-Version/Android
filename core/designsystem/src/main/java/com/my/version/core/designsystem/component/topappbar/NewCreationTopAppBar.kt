package com.my.version.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.component.button.MyVersionBasicButton
import com.my.version.core.designsystem.component.divider.BasicSpacer

@Composable
fun NewCreationTopAppBar(
    title: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
) {
    LogoTopAppBar(
        actions = listOf {
            MyVersionBasicButton(
                onClick = onClick
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = textStyle
                    )
                    BasicSpacer(width = 3.dp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = stringResource(id = R.string.btn_add)
                    )
                }
            }
        }
    )
}