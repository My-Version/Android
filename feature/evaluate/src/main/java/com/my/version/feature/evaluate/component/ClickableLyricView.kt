package com.my.version.feature.evaluate.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.MyVersionMain
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ClickableLyricView(
    timeStamp: Long,
    modifier: Modifier = Modifier,
    onClickLyric: (Int, Long) -> Unit = {_, _ -> },
    lyricIndex: Int = 0,
    lyrics: Map<Long, String> = emptyMap()
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(lyricIndex) {
        coroutineScope.launch {
            listState.animateScrollToItem(lyricIndex)
        }
    }



    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(vertical = 200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            lyrics.entries
                .toList()
        ) { index, item ->
            val color = if (item.key == timeStamp) MyVersionMain else Grey350
            Timber.tag("index").d("current indexes shown $index")

            Text(
                text = item.value,
                color = color,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.noRippleClickable {
                    onClickLyric(index, item.key)
                }
            )

            if (index < lyrics.size - 1) {
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}