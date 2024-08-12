package com.my.version.feature.cover.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.button.OutlinedButton
import com.my.version.core.designsystem.component.text.SingleLineText
import com.my.version.core.designsystem.theme.White
import com.my.version.feature.cover.R

@Composable
internal fun RowScope.OutlinedTextButton(
    onClick: () -> Unit,
    text: String,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .wrapContentHeight()
            .weight(1f),
    ) {
        SingleLineText(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = White,
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
    }
}