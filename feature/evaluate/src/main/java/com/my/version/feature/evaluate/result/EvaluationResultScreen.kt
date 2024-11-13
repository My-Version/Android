package com.my.version.feature.evaluate.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.core.designsystem.theme.White
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.result.component.ExpandableAudioItem
import com.my.version.feature.evaluate.result.state.EvaluationResultUiState

@Composable
fun EvaluationResultRoute(
    title: String,
    createdDate: String,
    similarity: Int,
    mostSimilarPeriod: Double,
    leastSimilarPeriod: Double,
    timeLength: Int,
    imageUrl: String,
    coverUrl: String,
    recordUrl: String,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: EvaluationResultViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.updateUiState(
            title = title,
            createdDate = createdDate,
            similarity = similarity,
            mostSimilarPeriod = mostSimilarPeriod,
            leastSimilarPeriod = leastSimilarPeriod,
            timeLength = timeLength,
            coverUrl = coverUrl,
            recordUrl = recordUrl,
            imageUrl = imageUrl
        )
    }

    EvaluationResultScreen(
        uiState = uiState,
        onClickCover = viewModel::coverClicked,
        onClickRecord = viewModel::recordClicked,
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
private fun EvaluationResultScreen(
    uiState: EvaluationResultUiState,
    navigateUp: () -> Unit,
    onClickCover: () -> Unit,
    onClickRecord: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = navigateUp,
            title = uiState.title
        )


        Column(
            modifier = modifier
                .wrapContentHeight()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicSpacer(height = 16.dp)
            Text(
                text = stringResource(id = R.string.evaluation_result_similarity),
                style = MyVersionTypography.titleMedium,
                color = Grey350
            )
            BasicSpacer(height = 16.dp)
            Text(
                text = "${uiState.similarity}%",
                style = MyVersionTypography.headlineLarge,
                color = MyVersionMain
            )
            BasicSpacer(height = 16.dp)
            Text(
                text = when (uiState.similarity) {
                    in 0..50 -> {
                        "Not Similar to AI cover"
                    }

                    in 51..75 -> {
                        "Somewhat Similar to AI cover"
                    }

                    in 76..100 -> {
                        "Very Similar to AI cover"
                    }

                    else -> {
                        "Error occured"
                    }
                },
                style = MyVersionTypography.bodyLarge,
                color = Grey350
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Grey200,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.evaluation_result_subtitle1),
                    style = MyVersionTypography.titleSmall,
                    color = Grey400,
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                )

                ExpandableAudioItem(
                    title = stringResource(id = R.string.evaluation_result_cover_title),
                    subTitle = "",//uiState.createdDate,
                    progress = uiState.progressCover,
                    isExpanded = uiState.isCoverEnabled,
                    backgroundColor = Grey200,
                    onClickItem = onClickCover
                )

                BasicSpacer(height = 20.dp)

                ExpandableAudioItem(
                    title = stringResource(id = R.string.evaluation_result_record_title),
                    subTitle = "",//uiState.createdDate,
                    progress = uiState.progressRecord,
                    isExpanded = uiState.isRecordEnabled,
                    backgroundColor = Grey200,
                    onClickItem = onClickRecord
                )

                BasicSpacer(height = 32.dp)

                Text(
                    text = stringResource(id = R.string.evaluation_result_subtitle2),
                    style = MyVersionTypography.titleSmall,
                    color = Grey400,
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                )

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(uiState.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                BasicSpacer(height = 32.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EvaluationResultScreenPreview() {
    MyVersionTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        )
        EvaluationResultScreen(
            navigateUp = {},
            onClickRecord = {},
            onClickCover = {},
            uiState = EvaluationResultUiState()
        )
    }
}