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
    navigateUp: () -> Unit,
    navigateToEvaluationUpload: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EvaluationRecordViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner)

    LaunchedEffect(true) {
        with(viewModel) {
            prepareMusic("Ditto-NewJeans.mp3")
            updateSongLyrics(
                LrcConverter.convertToLyricMap(
                    context.resources.openRawResource(
                        R.raw.ditto
                    )
                )
            )
        }
    }
    DisposableEffect(true) {
        onDispose {
            viewModel.stopMusic()
            viewModel.stopRecording()
        }
    }

    if (uiState.songLyrics.isNotEmpty()) {
        EvaluationRecordScreen(
            modifier = modifier,
            uiState = uiState,
            onPlayMusic = viewModel::onStartRecording,
            onStopMusic = viewModel::onStopRecording,
            onPressNextButton = { navigateToEvaluationUpload("") },
            onPressBackButton = navigateUp
        )
    }
}

@Composable
fun EvaluationRecordScreen(
    uiState: EvaluationRecordUiState,
    onPressNextButton: () -> Unit,
    onPressBackButton: () -> Unit,
    onPlayMusic: () -> Unit,
    onStopMusic: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)

    Column(
        modifier = modifier
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = onPressBackButton,
            title = stringResource(id = R.string.evaluation_topbar_record)
        )

        TitleWithDivider(
            text = stringResource(id = R.string.evaluation_on_boarding_title2),
            textStyle = MaterialTheme.typography.titleMedium,
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
            onResetButtonClick = {},
            onPlayButtonClick = onPlayMusic,
            onPauseButtonClick = onStopMusic
        )

        Spacer(modifier = Modifier.weight(1f))
        RectangleButton(
            isEnabled = uiState.isNextEnabled,
            text = "Next",
            textStyle = MaterialTheme.typography.titleMedium,
            innerPadding = 20,
            onClick = onPressNextButton,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Preview(showBackground = true,)
@Composable
private fun EvaluationRecordScreenPreview() {
    MyVersionTheme {
        EvaluationRecordScreen(
            modifier = Modifier.background(MyVersionBackground),
            onPressNextButton = {},
            onPressBackButton = {},
            onPlayMusic = {},
            onStopMusic = {},
            uiState = EvaluationRecordUiState()
        )
    }
}