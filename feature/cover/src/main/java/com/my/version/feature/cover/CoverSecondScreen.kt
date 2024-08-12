package com.my.version.feature.cover

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.button.OutlinedButton
import com.my.version.core.designsystem.component.divider.TitleWithDivider
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.type.TempItem
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.designsystem.type.tempList1

@Composable
fun CoverSecondRoute(
    modifier: Modifier = Modifier
) {
    CoverSecondScreen(
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CoverSecondScreen(
    modifier: Modifier = Modifier,
    fileList: List<TempItem> = emptyList()
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
                    text = stringResource(id = R.string.cover_on_boarding_title2),
                    textStyle = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    OutlinedButton(
                        text = stringResource(id = R.string.cover_button_audio_record),
                        textStyle = MaterialTheme.typography.titleMedium,
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedButton(
                        text = stringResource(id = R.string.cover_button_audio_record),
                        textStyle = MaterialTheme.typography.titleMedium,
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            items(fileList) { file ->
                MyVersionVerticalItem(
                    itemType = VerticalItemType.AUDIO,
                    iconColor = Black,
                    onClick = { /*TODO*/ },
                    title = file.title,
                    subTitle = file.body
                )
                if (fileList.last() != file) {
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
private fun CoverSecondScreenPreview() {
    MyVersionTheme {
        Box(
            modifier = Modifier.background(MyVersionBackground)
        ) {
            CoverSecondScreen(
                fileList = tempList1,
            )
        }
    }
}