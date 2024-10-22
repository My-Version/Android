package com.my.version.core.designsystem.component.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.button.MyVersionBasicIconButton
import com.my.version.core.designsystem.component.icon.MusicPlayIcon
import com.my.version.core.designsystem.component.text.SingleLineText
import com.my.version.core.designsystem.theme.MyVersionSub1
import com.my.version.core.designsystem.theme.MyVersionSub5
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.designsystem.R as DesignSystemR

@Composable
fun AudioPlayBox(
    title: String?,
    subTitle: String?,
    modifier: Modifier = Modifier,
    onClickPlayButton: () -> Unit = {},
    onClickPauseButton: () -> Unit = {},
    isPlaying: Boolean = false,
    colorList: List<Color> = listOf(
        MyVersionSub1, // 시작 색상
        MyVersionSub5 // 끝 색상
    )
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = colorList
                )
            )
            .padding(vertical = 16.dp)
            .padding(start = 30.dp, end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(end = 16.dp)
        ) {
            SingleLineText(
                text = title ?: "Audio Not Selected",
                color = White,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(6.dp))
            SingleLineText(
                text = subTitle ?: "None",
                color = White,
                style = MaterialTheme.typography.labelMedium,
            )
        }
        MusicPlayIcon(
            isPlaying = isPlaying,
            onClickPauseButton = onClickPauseButton,
            onClickPlayButton =  onClickPlayButton,
            iconColor = White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePlayBoxPreview() {
    MyVersionTheme {
        AudioPlayBox(
            title = "Title",
            subTitle = "Sub Title",
        )
    }
}