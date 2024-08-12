package com.my.version.core.designsystem.component.divider

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.theme.Grey300

@Composable
fun MyVersionHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = Grey300,
) {
    HorizontalDivider(
        modifier = modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth(),
        color = color,
        thickness = thickness
    )
}