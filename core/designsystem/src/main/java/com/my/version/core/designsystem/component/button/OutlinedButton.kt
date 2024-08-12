package com.my.version.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.my.version.core.designsystem.component.text.SingleLineText
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    borderColor: Color = Color.White,
    borderWidth: Dp = 1.dp,
    cornerRadius: Dp = 5.dp,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .noRippleClickable { onClick() }
            .background(containerColor)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(cornerRadius)
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedButtonPreview() {
    MyVersionTheme {
        Box(modifier = Modifier.background(MyVersionBackground)) {
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.width(145.dp)
            ) {
                SingleLineText(
                    text = "Record",
                    style = MaterialTheme.typography.bodyMedium,)
            }
        }
    }
}