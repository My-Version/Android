package com.my.version.feature.evaluate.select

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.divider.TitleWithDivider
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.type.TempItem
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.designsystem.type.tempList1
import com.my.version.feature.evaluate.R

@Composable
fun EvaluationSelectRoute(
    modifier: Modifier = Modifier,
    viewModel: EvaluationSelectViewModel = hiltViewModel()
) {
    EvaluationSelectScreen(
        modifier = modifier,
        coverList = tempList1
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EvaluationSelectScreen(
    modifier: Modifier = Modifier,
    coverList: List<TempItem> = emptyList()
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f),
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {

            stickyHeader {
                TitleWithDivider(
                    text = stringResource(id = R.string.evaluation_on_boarding_title1),
                    textStyle = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.background(color = MyVersionBackground)
                )
            }

            items(coverList) { cover ->

                MyVersionVerticalItem(
                    itemType = VerticalItemType.COVER,
                    iconColor = Black,
                    onClick = {
                    },
                    title = cover.title,
                    subTitle = cover.body
                )
                if (coverList.last() != cover) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Box(
            modifier = Modifier.wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            RectangleButton(
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
private fun EvaluationSelectScreenPreview() {
    MyVersionTheme {
        Box(modifier = Modifier.background(color = MyVersionBackground)) {
            EvaluationSelectScreen(
                coverList = tempList1
            )
        }
    }
}

