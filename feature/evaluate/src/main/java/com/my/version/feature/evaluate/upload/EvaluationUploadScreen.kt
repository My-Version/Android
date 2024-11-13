package com.my.version.feature.evaluate.upload

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.my.version.core.designsystem.component.dialog.ConfirmDialog
import com.my.version.core.designsystem.component.divider.MyVersionHorizontalDivider
import com.my.version.core.designsystem.component.divider.TitleWithDivider
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.component.ClickableLyricView
import com.my.version.feature.evaluate.upload.component.AudioControlButtonGroup
import com.my.version.feature.evaluate.upload.state.EvaluationUploadUiState
import timber.log.Timber
import com.my.version.core.designsystem.R as DesignSystemR

/**
 * 초 표시
 */

@Composable
fun EvaluationUploadRoute(
    coverId: Long,
    filePath: String,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EvaluationUploadViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner)

    LaunchedEffect(true) {
        Timber.tag("StreamMediaPlayer").d(filePath)
        with(viewModel) {
            setAudioData(
                filePath = "/storage/emulated/0/Android/data/com.my.version/files/Music/Ditto_NewJeans.mp3",
                songLyrics = LrcConverter.convertToLyricMap(context.resources.openRawResource(R.raw.ditto))
            )
            prepareAudio()
        }
    }

    EvaluationUploadScreen(
        modifier = modifier,
        uiState = uiState,
        onClickBack = {},
        onClickUpload = { viewModel.updateUploadDialogVisibility(true) },
        onClickPlay = viewModel::playAudio,
        onClickClose = viewModel::stopAudio,
        onChangeSlider = viewModel::changeSlider,
    )

    ConfirmDialog(
        text = stringResource(R.string.evaluation_upload_dialog_confirm),
        onDismissRequest = { viewModel.updateUploadDialogVisibility(false) },
        onConfirmRequest = onNavigateToHome,
        onUploadRequest = {
            viewModel.uploadFilesForEvaluation(coverId = coverId)
        },
        visibility = uiState.uploadDialogVisibility,
        loadState = uiState.dialogLoadState
    )

    DisposableEffect(true) {
        onDispose {
            /*TODO: AudioPlayer 종료*/
        }
    }
}

@Composable
private fun EvaluationUploadScreen(
    onClickBack: () -> Unit,
    onClickPlay: () -> Unit,
    onClickClose: () -> Unit,
    onClickUpload: () -> Unit,
    onChangeSlider: (Float) -> Unit,
    uiState: EvaluationUploadUiState,
    modifier: Modifier = Modifier,
) {
    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = onClickBack,
            title = stringResource(id = R.string.evaluation_topbar_upload)
        )

        TitleWithDivider(
            text = stringResource(id = R.string.evaluation_on_boarding_title2),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = commonModifier
        )

        ClickableLyricView(
            lyrics = uiState.songLyrics,
            timeStamp = uiState.currentTimeStamp,
            lyricIndex = uiState.lyricIndex,
            onClickLyric = { index, timeStamp ->
                onChangeSlider(timeStamp.toFloat() / uiState.fileLength)
            },
            modifier = commonModifier.height(450.dp)
        )

        MyVersionHorizontalDivider(
            modifier = commonModifier
        )

        Slider(
            value = uiState.progress,
            valueRange = 0f..1f,
            onValueChange = onChangeSlider,
            interactionSource = interactionSource,
            modifier = commonModifier
                .padding(vertical = 30.dp)
                .height(7.dp),
        )
        AudioControlButtonGroup(
            modifier = commonModifier,
            isPlaying = uiState.isPlaying,
            onClickPlay = onClickPlay,
            onClickPause = onClickPlay,
            onClickClose = onClickClose
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            RectangleButton(
                isEnabled = true,
                text = stringResource(id = DesignSystemR.string.btn_upload_capital),
                textStyle = MaterialTheme.typography.titleMedium,
                innerPadding = 20,
                onClick = onClickUpload,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EvaluationRecordScreenPreview() {
    MyVersionTheme {
        EvaluationUploadScreen(
            onClickClose = {},
            onClickBack = {},
            onClickUpload = {},
            onClickPlay = {},
            onChangeSlider = {},
            modifier = Modifier.background(MyVersionBackground),
            uiState = EvaluationUploadUiState()
        )
    }
}