package com.my.version.feature.evaluate.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.my.version.core.common.extension.showToast
import com.my.version.core.common.media.LrcConverter
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.divider.MyVersionHorizontalDivider
import com.my.version.core.designsystem.component.divider.TitleWithDivider
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.component.LyricView
import com.my.version.feature.evaluate.record.component.RecordButtonsRow
import com.my.version.feature.evaluate.record.state.EvaluationRecordUiState

/**
 * 수정사항
 * 1. 비율 수정
 * 2. 음악 이름 받아오기
 * 3. 시간대 표시? 할래말래
 */

@Composable
fun EvaluationRecordRoute(
    music: String,
    artist: String,
    coverId: Long,
    navigateUp: () -> Unit,
    navigateToEvaluationUpload: (String, Long) -> Unit,
    modifier: Modifier = Modifier,
    musicUriString: String = "Ditto-NewJeans.mp3",
    viewModel: EvaluationRecordViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is EvaluationRecordSideEffect.ShowToast ->
                        context.showToast(sideEffect.message)

                    is EvaluationRecordSideEffect.NavigateUp ->
                        navigateUp()

                    is EvaluationRecordSideEffect.NavigateToUpload ->
                        navigateToEvaluationUpload(sideEffect.recordId, coverId)
                }
            }
    }

    LaunchedEffect(true) {
        with(viewModel) {
            prepareMusic(uriString = musicUriString)
            prepareMusicLyrics(
                lyric = LrcConverter.convertToLyricMap(
                    context.resources.openRawResource(R.raw.ditto)
                )
            )
        }
    }

    if (uiState.songLyrics.isNotEmpty()) {
        EvaluationRecordScreen(
            modifier = modifier,
            uiState = uiState,
            music = music,
            artist = artist,
            onClickBackButton = viewModel::navigateUp,
            onClickResetButton = viewModel::resetPlayers,
            onClickPlayButton = viewModel::startPlayers,
            onClickNextButton = viewModel::navigateToUpload,
        )
    }

    DisposableEffect(true) {
        onDispose {
            viewModel.stopPlayers()
        }
    }

}

@Composable
fun EvaluationRecordScreen(
    music: String,
    artist: String,
    uiState: EvaluationRecordUiState,
    onClickNextButton: () -> Unit,
    onClickBackButton: () -> Unit,
    onClickResetButton: () -> Unit,
    onClickPlayButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)

    Column(
        modifier = modifier
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = onClickBackButton,
            title = stringResource(id = R.string.evaluation_topbar_record)
        )

        TitleWithDivider(
            text = "$artist - $music",
            textStyle = MaterialTheme.typography.titleSmall,
            modifier = commonModifier
        )

        LyricView(
            lyrics = uiState.songLyrics,
            timeStamp = uiState.currentTimeStamp,
            modifier = commonModifier.height(450.dp)
        )

        MyVersionHorizontalDivider(
            modifier = commonModifier
        )

        RecordButtonsRow(
            modifier = commonModifier.padding(top = 20.dp),
            isRecordEnabled = uiState.isRecordEnabled,
            onResetButtonClick = onClickResetButton,
            onPlayButtonClick = onClickPlayButton
        )

        Spacer(modifier = Modifier.weight(1f))
        RectangleButton(
            isEnabled = uiState.isNextEnabled,
            text = "Next",
            textStyle = MaterialTheme.typography.titleMedium,
            innerPadding = 20,
            onClick = onClickNextButton,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun EvaluationRecordScreenPreview() {
    MyVersionTheme {
        EvaluationRecordScreen(
            modifier = Modifier.background(MyVersionBackground),
            onClickNextButton = {},
            onClickBackButton = {},
            onClickPlayButton = {},
            onClickResetButton = {},
            uiState = EvaluationRecordUiState(),
            music = "Ditto",
            artist = "NewJeans"
        )
    }
}