package com.my.version.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.text.SingleLineText
import com.my.version.core.designsystem.theme.White

@Composable
fun HomeHorizontalList(
    title: String,
    composableList: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier
) {
    val spacerModifier = Modifier.height(6.dp)

    SingleLineText(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = White,
        modifier = modifier
    )

    Spacer(modifier = spacerModifier)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        items(composableList) { item ->
            item()
        }
    }

    Spacer(modifier = spacerModifier)

    HorizontalDivider(
        color = White,
        thickness = 1.dp,
        modifier = modifier
    )

}