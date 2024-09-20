package com.my.version.feature.evaluate.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.White

@Composable
fun LyricView(
    timeStamp: Long,
    modifier: Modifier = Modifier,
    lyrics: Map<Long, String> = emptyMap()
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(
                state = scrollState
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for ((time, lyric) in lyrics.entries.toList()) {
            val color = if(time == timeStamp) MyVersionMain else White
            Text(
                text = lyric,
                color = color,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}