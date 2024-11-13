package com.my.version.feature.evaluate.result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.my.version.core.designsystem.theme.TransparentGray
import com.my.version.core.designsystem.theme.White
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.result.component.ExpandableAudioItem
import com.my.version.feature.evaluate.result.state.EvaluationResultAudioState
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coverState by viewModel.coverState.collectAsStateWithLifecycle()
    val recordState by viewModel.recordState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        with(viewModel) {
            updateUiState(
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
    }


    EvaluationResultScreen(
        uiState = uiState,
        coverState = coverState,
        recordState = recordState,
        onClickCover = { viewModel.updateItemEnable(ResultType.COVER) },
        onClickCoverIcon = viewModel::onClickCoverIcon,
        onClickCoverMostSimilar = {
            viewModel.seekToSimilarPeriod(
                ResultType.COVER,
                uiState.mostSimilarPeriod
            )
        },
        onClickCoverLeastSimilar = {
            viewModel.seekToSimilarPeriod(
                ResultType.COVER,
                uiState.leastSimilarPeriod
            )
        },
        onClickRecord = { viewModel.updateItemEnable(ResultType.RECORD) },
        onClickRecordIcon = viewModel::onClickRecordIcon,
        onClickRecordMostSimilar = {
            viewModel.seekToSimilarPeriod(
                ResultType.RECORD,
                uiState.mostSimilarPeriod
            )
        },
        onClickRecordLeastSimilar = {
            viewModel.seekToSimilarPeriod(
                ResultType.RECORD,
                uiState.leastSimilarPeriod
            )
        },
        onChangeCoverProgress = { viewModel.updateProgress(ResultType.COVER, it) },
        onChangeRecordProgress = { viewModel.updateProgress(ResultType.RECORD, it) },
        navigateUp = navigateUp,
        modifier = modifier
    )

    if (!(coverState.prepared || recordState.prepared)) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = TransparentGray)
                .clickable(enabled = false) {}
        )
    }


    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopPlayer()
        }
    }
}

@Composable
private fun EvaluationResultScreen(
    uiState: EvaluationResultUiState,
    coverState: EvaluationResultAudioState,
    recordState: EvaluationResultAudioState,
    navigateUp: () -> Unit,
    onClickCover: () -> Unit,
    onClickRecord: () -> Unit,
    onClickCoverIcon: () -> Unit,
    onClickRecordIcon: () -> Unit,
    onClickCoverMostSimilar: () -> Unit,
    onClickCoverLeastSimilar: () -> Unit,
    onClickRecordMostSimilar: () -> Unit,
    onClickRecordLeastSimilar: () -> Unit,
    onChangeCoverProgress: (Float) -> Unit,
    onChangeRecordProgress: (Float) -> Unit,
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
                    progress = coverState.progress,
                    isExpanded = coverState.isExpanded,
                    isPlaying = coverState.isPlaying,
                    backgroundColor = Grey200,
                    onClickItem = onClickCover,
                    onClickIcon = onClickCoverIcon,
                    onSliderValueChange = onChangeCoverProgress,
                    onClickLeastSimilar = onClickCoverLeastSimilar,
                    onClickMostSimilar = onClickCoverMostSimilar
                )

                BasicSpacer(height = 20.dp)

                ExpandableAudioItem(
                    title = stringResource(id = R.string.evaluation_result_record_title),
                    subTitle = "",//uiState.createdDate,
                    progress = recordState.progress,
                    isExpanded = recordState.isExpanded,
                    isPlaying = recordState.isPlaying,
                    backgroundColor = Grey200,
                    onClickItem = onClickRecord,
                    onClickIcon = onClickRecordIcon,
                    onSliderValueChange = onChangeRecordProgress,
                    onClickLeastSimilar = onClickRecordLeastSimilar,
                    onClickMostSimilar = onClickRecordMostSimilar
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
            onClickCoverIcon = {},
            onClickRecordIcon = {},
            uiState = EvaluationResultUiState(),
            coverState = EvaluationResultAudioState(),
            recordState = EvaluationResultAudioState(),
            onChangeCoverProgress = {},
            onChangeRecordProgress = {},
            onClickCoverLeastSimilar = {},
            onClickCoverMostSimilar = {},
            onClickRecordLeastSimilar = {},
            onClickRecordMostSimilar = {}
        )
    }
}