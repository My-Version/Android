package com.my.version.core.designsystem.component.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.component.button.MyVersionBasicIconButton
import com.my.version.core.designsystem.theme.White
import com.my.version.core.designsystem.type.VerticalItemType

@Composable
fun MusicPlayIcon(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    onClickPlayButton: () -> Unit,
    onClickPauseButton: () -> Unit,
    iconColor: Color = White
) {
    Icon(
        imageVector = if(isPlaying) {
            ImageVector.vectorResource(R.drawable.ic_pause)
        } else {
            ImageVector.vectorResource(R.drawable.ic_play)
        },
        contentDescription = stringResource(id = VerticalItemType.MUSIC.contentDescription),
        tint = iconColor,
        modifier = modifier.noRippleClickable {
            if (isPlaying) {
                onClickPauseButton()
            } else {
                onClickPlayButton()
            }
        }
    )
}