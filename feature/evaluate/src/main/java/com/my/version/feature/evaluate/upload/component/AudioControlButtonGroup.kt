package com.my.version.feature.evaluate.upload.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.component.button.MyVersionBasicIconButton
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.MyVersionSub3

@Composable
internal fun AudioControlButtonGroup(
    isPlaying: Boolean,
    onClickClose: () -> Unit,
    onClickPlay: () -> Unit,
    onClickPause: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyVersionBasicIconButton(
            icon = R.drawable.ic_close,
            onClick = onClickClose,
            color = Grey200
        )

        MyVersionBasicIconButton(
            icon = if(isPlaying) R.drawable.ic_pause else R.drawable.ic_play,
            onClick = if(isPlaying) onClickPause else onClickPlay,
            color = MyVersionSub3,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AudioControlButtonGroupPreview() {
    AudioControlButtonGroup(
        modifier = Modifier.fillMaxWidth(),
        isPlaying = true,
        onClickClose = {},
        onClickPlay = {},
        onClickPause = {}
    )
}