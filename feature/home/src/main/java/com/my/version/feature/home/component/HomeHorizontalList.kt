package com.my.version.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.text.SingleLineText
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White
import com.my.version.feature.home.tempList1

@Composable
fun HomeHorizontalList(
    title: String,
    composableList: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier
) {
    val spacerModifier = modifier.height(6.dp)

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
        color = Grey400,
        thickness = 1.dp,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun HomeHorizontalListPreview() {
    MyVersionTheme {
        Column(
            modifier = Modifier.background(color = MyVersionBackground)
        ) {
            HomeHorizontalList(
                title = "title",
                composableList = evaluationToComposableList(evaluationList = tempList1),
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}