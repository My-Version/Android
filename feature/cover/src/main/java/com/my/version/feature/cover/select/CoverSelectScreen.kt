package com.my.version.feature.cover.select

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.my.version.core.common.state.UiState
import com.my.version.core.designsystem.component.bottomsheet.SortingBottomSheet
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.button.SortingButton
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.type.SortBy
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.domain.entity.MusicAudioFile
import com.my.version.feature.cover.R
import com.my.version.feature.cover.select.state.CoverSelectUiState

@Composable
fun CoverSelectRoute(
    navigateUp: () -> Unit,
    navigateToUpload: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CoverSelectViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when(sideEffect) {
                    is CoverSelectSideEffect.NavigateUp -> navigateUp()
                    is CoverSelectSideEffect.NavigateNext -> navigateToUpload()

                }
            }
    }

    CoverSelectScreen(
        modifier = modifier,
        uiState = uiState,
        onItemClicked = viewModel::onClickMusic,
        onNavigateUp = viewModel::navigateUp,
        onChangeSortBy = viewModel::updateSortByIndex,
        onChangeSortSheetVisibility = viewModel::updateSheetVisibility,
        onNextClicked = viewModel::navigateToUpload
    )
}

@Composable
fun CoverSelectScreen(
    modifier: Modifier = Modifier,
    uiState: CoverSelectUiState,
    onItemClicked: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    onNextClicked: () -> Unit,
    onChangeSortBy: (Int) -> Unit,
    onChangeSortSheetVisibility: (Boolean) -> Unit
) {
    if (uiState.isSortSheetVisible) {
        SortingBottomSheet(
            onDismiss = { index ->
                onChangeSortSheetVisibility(false)
                onChangeSortBy(index)
            },
            onSelectSortBy = onChangeSortBy,
            initialSortBy = uiState.sortByIndex
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = onNavigateUp,
            title = stringResource(id = R.string.cover_topbar_selection)
        )

        BasicSpacer(height = 20.dp)

        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.cover_main_title),
                style = MaterialTheme.typography.titleSmall,
                color = Black
            )
            Spacer(modifier = Modifier.weight(1f))

            SortingButton(
                text = stringResource(SortBy.entries[uiState.sortByIndex].sortBy),
                onClick = { onChangeSortSheetVisibility(true) }
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Grey200,
            modifier = Modifier.padding(
                horizontal = 20.dp
            )
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (uiState.loadState) {
                UiState.Loading -> {}
                UiState.Empty -> {}
                is UiState.Failure -> {}
                is UiState.Success -> {
                    SuccessScreen(
                        musicList = uiState.loadState.data,
                        selectIndex = uiState.selected,
                        onMusicSelected = onItemClicked
                    )
                }

            }
        }

        RectangleButton(
            isEnabled = uiState.selected != -1,
            text = "Next",
            textStyle = MaterialTheme.typography.titleMedium,
            innerPadding = 20,
            modifier = Modifier.fillMaxWidth(),
            onClick = onNextClicked
        )
    }
}

@Composable
private fun SuccessScreen(
    musicList: List<MusicAudioFile>,
    selectIndex: Int,
    onMusicSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        itemsIndexed(musicList) { index, cover ->
            val selected = selectIndex == index

            MyVersionVerticalItem(
                itemType = VerticalItemType.MUSIC,
                iconColor = if (selected) MyVersionMain else Black,
                onClick = { onMusicSelected(index) },
                title = cover.title,
                subTitle = cover.artist
            )
            if (index < musicList.size - 1) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}