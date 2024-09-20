package com.my.version.feature.evaluate.select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.divider.TitleWithDivider
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.select.state.EvaluationSelectUiState

@Composable
fun EvaluationSelectRoute(
    navigateUp: () -> Unit,
    navigateToRecord:() -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EvaluationSelectViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    EvaluationSelectScreen(
        modifier = modifier,
        uiState = uiState,
        onItemClicked = {},
        onNextClicked = navigateToRecord,
        onBackPressed = navigateUp
    )
}

@Composable
private fun EvaluationSelectScreen(
    onNextClicked: () -> Unit,
    onItemClicked: () -> Unit,
    onBackPressed: () -> Unit,
    uiState: EvaluationSelectUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = onBackPressed,
            title = stringResource(id = R.string.evaluation_topbar_selection)
        )

        TitleWithDivider(
            text = stringResource(id = R.string.evaluation_on_boarding_title1),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .background(color = MyVersionBackground)
                .padding(horizontal = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            itemsIndexed(uiState.musicList) { index, cover ->
                MyVersionVerticalItem(
                    itemType = VerticalItemType.COVER,
                    iconColor = Black,
                    onClick = {
                    },
                    title = cover.title,
                    subTitle = cover.createdDate
                )
                if (index < uiState.musicList.lastIndex) {
                    BasicSpacer(height = 16.dp)
                }
            }
        }

        Box(
            modifier = Modifier.wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            RectangleButton(
                isEnabled = true,
                text = "Next",
                textStyle = MaterialTheme.typography.titleMedium,
                innerPadding = 20,
                modifier = Modifier.fillMaxWidth(),
                onClick = onNextClicked
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
                uiState = EvaluationSelectUiState(),
                onBackPressed = {},
                onNextClicked = {},
                onItemClicked = {}
            )
        }
    }
}

