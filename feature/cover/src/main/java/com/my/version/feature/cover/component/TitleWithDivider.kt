package com.my.version.feature.cover.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.divider.MyVersionHorizontalDivider
import com.my.version.core.designsystem.component.text.SingleLineText
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.White
import com.my.version.feature.cover.R

@Composable
fun TitleWithDivider(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(MyVersionBackground)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        SingleLineText(
            color = White,
            text = text,
            style = textStyle,
            modifier = Modifier.padding(start = 10.dp)
        )
        MyVersionHorizontalDivider()
    }
}