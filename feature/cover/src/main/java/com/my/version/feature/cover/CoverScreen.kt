package com.my.version.feature.cover

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.my.version.core.designsystem.component.button.SortingButton
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey300
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White
import com.my.version.core.designsystem.type.TempItem
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.designsystem.type.tempList1

@Composable
fun CoverRoute(
    modifier: Modifier = Modifier,
    viewModel: CoverViewModel = hiltViewModel()
) {
    CoverScreen(
        modifier = modifier,
        coverList = tempList1
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CoverScreen(
    modifier: Modifier = Modifier,
    coverList: List<TempItem> = emptyList()
) {
    var isSelected by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)

    ) {
        item{
            Spacer(modifier = Modifier.height(20.dp))
        }
        stickyHeader {
            Row(
                modifier = Modifier
                    .background(color = MyVersionBackground)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.cover_main_title),
                    style = MaterialTheme.typography.titleSmall,
                    color = White
                )
                SortingButton(
                    isSelected = isSelected,
                    text = "최신순",
                    onClick = { isSelected = !isSelected }
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = Grey400,
                modifier = Modifier
                    .background(color = MyVersionBackground)
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }

        if (coverList.isEmpty()) {
            item {
                EmptyScreen()
            }
        } else {
            items(coverList) { cover ->
                MyVersionVerticalItem(
                    itemType = VerticalItemType.COVER,
                    iconColor = Black,
                    onClick = { /*TODO*/ },
                    title = cover.title,
                    subTitle = cover.body
                )
                if (coverList.last() != cover) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "You have not created any AI cover",
            textAlign = TextAlign.Center,
            color = Grey300,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 70.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuccessScreenPreview() {
    MyVersionTheme {
        CoverScreen(
            coverList = tempList1,
            modifier = Modifier.background(MyVersionBackground)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyScreenPreview() {
    MyVersionTheme {
        CoverScreen(
            modifier = Modifier.background(MyVersionBackground)
        )
    }
}
