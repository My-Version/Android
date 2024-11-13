package com.my.version.feature.evaluate.result.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.component.slider.MyVersionSlider
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.core.designsystem.R as DesignSystemR

@Composable
fun ExpandableAudioItem(
    title: String,
    subTitle: String,
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onClickItem: () -> Unit = {},
    onClickIcon: () -> Unit = {},
    onClickMostSimilar: () -> Unit = {},
    onClickLeastSimilar: () -> Unit = {},
    onSliderValueChange: (Float) -> Unit = {},
    isExpanded: Boolean = false,
    isPlaying: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .noRippleClickable {
                onClickItem()
            }
            .animateContentSize(animationSpec = spring(stiffness = Spring.StiffnessMedium))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    color = Black,
                    style = MyVersionTypography.bodyLarge
                )
            }

            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (isPlaying)
                        com.my.version.core.designsystem.R.drawable.ic_pause
                    else
                        com.my.version.core.designsystem.R.drawable.ic_play
                ),
                tint = Black,
                contentDescription = "",
                modifier = Modifier.noRippleClickable (onClickIcon)
            )
        }

        if (isExpanded) {
            MyVersionSlider(
                progress = progress,
                onValueChange = onSliderValueChange
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                RoundedIconButton(
                    iconRes = DesignSystemR.drawable.ic_thumbs_up_16,
                    text = "Most Similar",
                    modifier = Modifier.weight(1f),
                    onClick = onClickMostSimilar
                )

                RoundedIconButton(
                    iconRes = DesignSystemR.drawable.ic_thumbs_down_16,
                    text = "Least Similar",
                    modifier = Modifier.weight(1f),
                    onClick = onClickLeastSimilar
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpandableAudioItemPreview() {
    MyVersionTheme {
        ExpandableAudioItem(
            title = "title",
            subTitle = "subTitle",
            progress = 0.4f,
            isExpanded = true,
            backgroundColor = Grey200,
            onClickItem = {}
        )
    }
}