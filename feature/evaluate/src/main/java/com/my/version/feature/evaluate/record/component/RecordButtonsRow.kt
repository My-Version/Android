package com.my.version.feature.evaluate.record.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.component.button.MyVersionBasicIconButton
import com.my.version.core.designsystem.theme.Grey200

@Composable
internal fun RecordButtonsRow(
    isRecordEnabled: Boolean,
    modifier: Modifier = Modifier,
    onResetButtonClick: () -> Unit = {},
    onPlayButtonClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyVersionBasicIconButton(
            icon = R.drawable.ic_play_back,
            onClick = onResetButtonClick,
            color = Grey200
        )

        Box(
            modifier = Modifier
                .background(
                    color = Grey200,
                    shape = CircleShape
                )
                .wrapContentSize()
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            MyVersionBasicIconButton(
                icon = R.drawable.ic_record_32,
                color = if (isRecordEnabled) Color.Red else Grey200,
                onClick = onPlayButtonClick,
                enabled = isRecordEnabled
            )
        }
    }
}

@Preview
@Composable
private fun RecordButtonsRowPreview() {
    RecordButtonsRow(
        modifier = Modifier.fillMaxWidth(),
        isRecordEnabled = true
    )
}