package com.my.version.core.designsystem.component.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.text.SingleLineText
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White

@Composable
fun TitleWithDivider(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    textColor: Color = Black
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SingleLineText(
            text = text,
            style = textStyle,
            color = textColor,
            modifier = Modifier.padding(start = 10.dp)
        )

        BasicSpacer(height = 12.dp)

        HorizontalDivider(
            thickness = 1.dp,
            color = Grey200
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TitleWithDividerPreview() {
    MyVersionTheme {
        Box(modifier = Modifier.background(color = MyVersionBackground)) {
            TitleWithDivider(
                text = "Cover List",
                textStyle = MaterialTheme.typography.titleMedium
            )
        }
    }
}

