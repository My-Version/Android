package com.my.version.feature.cover.upload.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.theme.MyVersionSub5

@Composable
fun CoverUploadIconButton(
    color: Color,
    contentDescription: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .wrapContentSize()
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(5.dp))
    ) {
        Icon(
            painter = icon,
            tint = color,
            contentDescription = contentDescription
        )
    }
}