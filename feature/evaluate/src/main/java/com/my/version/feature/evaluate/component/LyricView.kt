package com.my.version.feature.evaluate.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.White
import kotlinx.coroutines.launch

@Composable
fun LyricView(
    timeStamp: Long,
    modifier: Modifier = Modifier,
    lyrics: Map<Long, String> = emptyMap()
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(vertical = 200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(lyrics.entries.toList()) { index, item ->
            val color = if(item.key == timeStamp) {
                MyVersionMain
            }
            else Grey350
            LaunchedEffect(timeStamp) {
                if(item.key == timeStamp) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index)
                    }
                }
            }

            Text(
                text = item.value,
                color = color,
                style = MaterialTheme.typography.titleMedium
            )

            if(index < lyrics.size-1) {
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}