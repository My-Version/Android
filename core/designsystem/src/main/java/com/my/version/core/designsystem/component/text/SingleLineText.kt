package com.my.version.core.designsystem.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.White

@Composable
fun SingleLineText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = Black,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = text,
        style = style,
        textAlign = textAlign,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Clip,
        modifier = modifier
    )
}