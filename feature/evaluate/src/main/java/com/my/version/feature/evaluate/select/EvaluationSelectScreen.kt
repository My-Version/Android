package com.my.version.feature.evaluate.select

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.common.extension.showToast
import com.my.version.core.common.state.UiState
import com.my.version.core.designsystem.component.bottomsheet.SortingBottomSheet
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.button.SortingButton
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.Grey300
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.type.SortBy
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.domain.entity.CoverAudio
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.select.state.EvaluationSelectUiState

@Composable
fun EvaluationSelectRoute(
    navigateUp: () -> Unit,
    navigateToRecord: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EvaluationSelectViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is EvaluationSelectSideEffect.ShowToast -> context.showToast(sideEffect.message)

                is EvaluationSelectSideEffect.NavigateUp -> navigateUp()

                is EvaluationSelectSideEffect.NavigateNext -> navigateToRecord()
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.getCoverList()
    }

    EvaluationSelectScreen(
        modifier = modifier,
        uiState = uiState,
        onItemClicked = viewModel::onCoverSelected,
        onNextClicked = viewModel::navigateToRecord,
        onBackPressed = viewModel::navigateUp,
        onSortSheetVisibilityChanged = viewModel::updateSheetVisibility,
        onSortByChanged = viewModel::updateSortByIndex
    )

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopPlayer()
        }
    }
}

@Composable
private fun EvaluationSelectScreen(
    onNextClicked: () -> Unit,
    onItemClicked: (CoverAudio) -> Unit,
    onBackPressed: () -> Unit,
    onSortSheetVisibilityChanged: (Boolean) -> Unit,
    onSortByChanged: (Int) -> Unit,
    uiState: EvaluationSelectUiState,
    modifier: Modifier = Modifier
) {
    if (uiState.isSortSheetVisible) {
        SortingBottomSheet(
            onDismiss = { index ->
                onSortSheetVisibilityChanged(false)
                onSortByChanged(index)
            }, onSelectSortBy = onSortByChanged, initialSortBy = uiState.sortByIndex
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = onBackPressed,
            title = stringResource(id = R.string.evaluation_topbar_selection)
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
                text = stringResource(id = R.string.evaluation_on_boarding_title1),
                style = MaterialTheme.typography.titleSmall,
                color = Black
            )
            Spacer(modifier = Modifier.weight(1f))

            SortingButton(text = stringResource(SortBy.entries[uiState.sortByIndex].sortBy),
                onClick = { onSortSheetVisibilityChanged(true) })
        }

        HorizontalDivider(
            thickness = 1.dp, color = Grey200, modifier = Modifier.padding(
                horizontal = 20.dp
            )
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f)
        ) {
            when (uiState.loadState) {
                is UiState.Empty -> EmptyScreen()
                is UiState.Failure -> {}
                is UiState.Loading -> {}
                is UiState.Success -> SuccessScreen(
                    coverList = uiState.loadState.data,
                    selectedCover = uiState.selectedCover,
                    onItemClicked = onItemClicked
                )

            }
        }



        RectangleButton(
            isEnabled = uiState.selectedCover != null,
            text = "Next",
            textStyle = MaterialTheme.typography.titleMedium,
            innerPadding = 20,
            modifier = Modifier.fillMaxWidth(),
            onClick = onNextClicked
        )

    }
}

@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.evaluation_select_empty),
            textAlign = TextAlign.Center,
            color = Grey350,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 70.dp)
        )

        BasicSpacer(height = 20.dp)

        Text(
            text = stringResource(id = R.string.evaluation_select_empty_guide1),
            textAlign = TextAlign.Center,
            color = Grey300,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun SuccessScreen(
    onItemClicked: (CoverAudio) -> Unit,
    coverList: List<CoverAudio>,
    selectedCover: CoverAudio?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        itemsIndexed(coverList) { index, cover ->
            if (cover.audio.isNotBlank()) {
                val color = if (cover == selectedCover) MyVersionMain else Black

                MyVersionVerticalItem(itemType = VerticalItemType.COVER,
                    iconColor = color,
                    onClick = { onItemClicked(cover) },
                    title = cover.title,
                    subTitle = cover.createdDate,
                    modifier = Modifier.noRippleClickable {
                        onItemClicked(cover)
                    })
                if (index < coverList.lastIndex) {
                    BasicSpacer(height = 16.dp)
                }
            }
        }
    }
}

