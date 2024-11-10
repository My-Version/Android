package com.my.version.core.designsystem.component.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.theme.Grey300
import com.my.version.core.designsystem.theme.MyVersionSub1
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.MyVersionTypography
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GuideDialog(
    title: String,
    guides: List<String>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    val pagerState = rememberPagerState { guides.size }
    val coroutineScope = rememberCoroutineScope()

    MyVersionBasicDialog(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MyVersionTypography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = guides[page],
                    style = MyVersionTypography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            RectangleButton(
                text = if (pagerState.currentPage == 0) "Close"
                else "Previous",
                onClick = {
                    if (pagerState.currentPage == 0) {
                        onDismiss()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage - 1
                            )
                        }
                    }
                },
                textStyle = MyVersionTypography.bodyMedium,
                innerPadding = 10,
                cornerRadius = 10.dp,
                isEnabled = true,
                backgroundColor = Grey300,
                modifier = Modifier.weight(1f)
            )
            RectangleButton(
                text = if (pagerState.currentPage == guides.size - 1) "Finish"
                    else "Next",
                onClick = {
                    if (pagerState.currentPage == guides.size - 1) {
                        onDismiss()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1
                            )
                        }
                    }
                },
                textStyle = MyVersionTypography.bodyMedium,
                innerPadding = 10,
                cornerRadius = 10.dp,
                isEnabled = true,
                backgroundColor = MyVersionSub1,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GuideDialogPreview() {
    MyVersionTheme {
        GuideDialog(
            title = "guide",
            guides = listOf(
                "content1", "content2", "content3"
            )
        )
    }
}
