package com.my.version.feature.cover.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.my.version.core.common.state.UiState
import com.my.version.core.designsystem.component.button.SortingButton
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.component.topappbar.NewCreationTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey300
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.White
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.feature.cover.R
import com.my.version.feature.cover.main.state.CoverUiState
import java.io.File

@Composable
fun CoverRoute(
    modifier: Modifier = Modifier,
    viewModel: CoverViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = lifecycleOwner
    )

    CoverScreen(
        modifier = modifier,
        uiState = uiState,
        onCoverSelected = {
            viewModel.playCoverAudio(it)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CoverScreen(
    modifier: Modifier = Modifier,
    uiState: CoverUiState,
    onCoverSelected: (File?) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }
    Column {
        NewCreationTopAppBar(
            title = stringResource(
                id = R.string.cover_main_title
            ),
            textStyle = MaterialTheme.typography.labelLarge,
            onClick = {}
        )


        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)

        ) {
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

            when (uiState.loadState) {
                UiState.Loading -> {}
                UiState.Empty -> {
                    item {
                        EmptyScreen()
                    }
                }

                is UiState.Failure -> {}
                is UiState.Success -> {
                    val coverList = uiState.loadState.data

                    items(coverList) { cover ->
                        MyVersionVerticalItem(
                            itemType = VerticalItemType.COVER,
                            iconColor = Black,
                            onClick = { onCoverSelected(cover.audio) },
                            title = cover.title,
                            subTitle = stringResource(id = R.string.cover_created_date, cover.createdDate)
                        )
                        if (coverList.last() != cover) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
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
