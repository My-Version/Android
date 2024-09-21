package com.my.version.feature.evaluate.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.my.version.core.common.state.UiState
import com.my.version.core.designsystem.component.bottomsheet.SortingBottomSheet
import com.my.version.core.designsystem.component.button.SortingButton
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.component.topappbar.NewCreationTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.CoverGradient1
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.type.SortBy
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.domain.entity.EvaluationResult
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.main.state.EvaluationUiState

@Composable
fun EvaluationRoute(
    navigateToSelect: () -> Unit,
    navigateToResult: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EvaluationViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner)

    EvaluationScreen(
        modifier = modifier,
        uiState = uiState,
        onCreateClicked = navigateToSelect,
        onEvaluationSelected = { index ->

        },
        onChangeSortBy = viewModel::updateSortByIndex,
        onChangeSortSheetVisibility = viewModel::updateSheetVisibility
    )
}

@Composable
private fun EvaluationScreen(
    uiState: EvaluationUiState,
    onCreateClicked: () -> Unit,
    onEvaluationSelected: (Int) -> Unit,
    onChangeSortBy: (Int) -> Unit,
    onChangeSortSheetVisibility: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val commonModifier = Modifier.padding(horizontal = 20.dp)

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
        modifier = modifier
    ) {
        NewCreationTopAppBar(
            title = stringResource(id = R.string.evaluation_topbar_main),
            textStyle = MaterialTheme.typography.labelLarge,
            onClick = onCreateClicked
        )

        BasicSpacer(height = 20.dp)

        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = com.my.version.core.designsystem.R.drawable.ic_music),
                contentDescription = "",
                tint = CoverGradient1
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(id = R.string.evaluation_main_title),
                style = MaterialTheme.typography.titleSmall,
                color = Black
            )

            Spacer(modifier = Modifier.weight(1f))

            SortingButton(
                text = stringResource(SortBy.entries[uiState.sortByIndex].sortBy),
                onClick = { onChangeSortSheetVisibility(true) }
            )
        }

        BasicSpacer(height = 12.dp)

        HorizontalDivider(
            thickness = 1.dp,
            color = Grey200,
            modifier = Modifier.padding(
                horizontal = 20.dp
            )
        )


        when (uiState.loadState) {
            is UiState.Loading -> {}
            is UiState.Empty -> EmptyScreen(modifier = commonModifier)
            is UiState.Failure -> {}
            is UiState.Success -> {
                SuccessScreen(
                    onEvaluationSelected = onEvaluationSelected,
                    evaluationList = uiState.loadState.data,
                    modifier = commonModifier
                )
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
            text = stringResource(id = R.string.evaluation_main_empty),
            textAlign = TextAlign.Center,
            color = Grey350,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 70.dp)
        )
    }
}

@Composable
private fun SuccessScreen(
    onEvaluationSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    evaluationList: List<EvaluationResult> = emptyList()
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        itemsIndexed(evaluationList) { index, cover ->
            MyVersionVerticalItem(
                itemType = VerticalItemType.EVALUATION,
                iconColor = Black,
                onClick = { onEvaluationSelected(index) },
                title = cover.title,
                subTitle = cover.date
            )
            if (index < evaluationList.size - 1) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

