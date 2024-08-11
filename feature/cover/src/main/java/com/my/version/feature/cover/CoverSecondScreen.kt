package com.my.version.feature.cover

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.button.OnBoardingButton
import com.my.version.core.designsystem.component.item.VerticalListItem
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.type.TempItem
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.designsystem.type.tempList1
import com.my.version.feature.cover.component.TitleWithDivider

// Next 버튼이랑 리스트랑 겹치는 부분을 어떻게 처리할지 고민해보던 중에 덮음

@Composable
fun CoverSecondRoute(
    modifier: Modifier = Modifier,
) {
    CoverSecondScreen(
        modifier = modifier,
        musicList = tempList1
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CoverSecondScreen(
    modifier: Modifier = Modifier,
    musicList: List<TempItem> = emptyList()
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {

            stickyHeader {
                TitleWithDivider(
                    text = stringResource(id = R.string.cover_on_boarding_title1),
                    textStyle = MaterialTheme.typography.titleMedium
                )
            }

            items(musicList) { cover ->
                VerticalListItem(
                    itemType = VerticalItemType.MUSIC,
                    iconColor = Black,
                    onClick = { /*TODO*/ },
                    title = cover.title,
                    subTitle = cover.body
                )
                if (musicList.last() != cover) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Box(
            modifier = Modifier.wrapContentHeight().padding(top = 6.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            OnBoardingButton(
                isEnabled = false,
                text = "Next",
                textStyle = MaterialTheme.typography.titleMedium,
                innerPadding = 20,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoverSecondScreenPreview() {
    MyVersionTheme {
        CoverSecondScreen(
            musicList = tempList1,
        )
    }
}