package com.my.version.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.theme.Grey300
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White

@Composable
fun RectangleButton(
    isEnabled: Boolean,
    text: String,
    textStyle: TextStyle,
    innerPadding: Int,
    modifier: Modifier = Modifier,
    textColor: Color = White,
    cornerRadius: Dp = 0.dp,
    onClick: () -> Unit = {},
) {
    val backgroundColor = if (isEnabled) {
        MyVersionMain
    } else {
        Grey300
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .noRippleClickable {
                if (isEnabled) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            modifier = Modifier.padding(vertical = innerPadding.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RectangleButtonPreview() {
    MyVersionTheme {
        RectangleButton(
            isEnabled = true,
            text = "Next",
            textStyle = MaterialTheme.typography.titleLarge,
            innerPadding = 20,
            modifier = Modifier.fillMaxWidth()
        )
    }
}