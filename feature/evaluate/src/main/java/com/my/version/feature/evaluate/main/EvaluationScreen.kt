package com.my.version.feature.evaluate.main

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.my.version.core.common.state.UiState
import com.my.version.core.designsystem.component.button.SortingButton
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.component.topappbar.NewCreationTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey300
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White
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
        onEvaluationSelected = navigateToSelect
    )
}

@Composable
private fun EvaluationScreen(
    uiState: EvaluationUiState,
    onCreateClicked: () -> Unit,
    onEvaluationSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val commonModifier = Modifier.padding(horizontal = 20.dp)
    var isSelected by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        NewCreationTopAppBar(
            title = stringResource(id = R.string.evaluation_topbar_main),
            textStyle = MaterialTheme.typography.labelLarge,
            onClick = onCreateClicked
        )

        BasicSpacer(height = 30.dp)

        Row(
            modifier = commonModifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.evaluation_main_title),
                style = MaterialTheme.typography.titleSmall,
                color = White
            )
            SortingButton(
                isSelected = isSelected,
                text = "최신순",
                onClick = { isSelected = !isSelected },
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Grey400,
            modifier = commonModifier
                .padding(vertical = 6.dp)
        )

        when(uiState.loadState) {
            is UiState.Loading -> {}
            is UiState.Empty -> EmptyScreen(modifier = commonModifier)
            is UiState.Failure -> {}
            is UiState.Success -> {
                SuccessScreen(
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
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "You have not created any evaluation",
            textAlign = TextAlign.Center,
            color = Grey300,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
private fun SuccessScreen(
    modifier: Modifier = Modifier,
    evaluationList: List<EvaluationResult> = emptyList()
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        items(evaluationList) { cover ->
            MyVersionVerticalItem(
                itemType = VerticalItemType.EVALUATION,
                iconColor = Black,
                onClick = { /*TODO*/ },
                title = cover.title,
                subTitle = cover.date
            )
            if (evaluationList.last() != cover) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuccessScreenPreview() {
    MyVersionTheme {
        EvaluationScreen(
            uiState = EvaluationUiState(),
            onCreateClicked = {},
            onEvaluationSelected = {},
            modifier = Modifier.background(MyVersionBackground)
        )
    }
}

