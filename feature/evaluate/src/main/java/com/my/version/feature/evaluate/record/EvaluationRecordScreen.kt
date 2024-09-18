package com.my.version.feature.evaluate.record

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.my.version.core.common.media.LrcConverter
import com.my.version.core.designsystem.component.button.MyVersionBasicIconButton
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.divider.MyVersionHorizontalDivider
import com.my.version.core.designsystem.component.divider.TitleWithDivider
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.record.model.EvaluationRecordUiState
import com.my.version.core.designsystem.R as DesignSystemR

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
        viewModel.updateSongLyrics(LrcConverter.convertToLyricMap(context.resources.openRawResource(R.raw.ditto)))
    }

    if(uiState.songLyrics.isNotEmpty()) {
        EvaluationRecordScreen(
            modifier = modifier,
            onPressNextButton = {
                viewModel.getRecordFilePath()?.let {
                    navigateToEvaluationUpload(it)
                }
            },
            onPressBackButton = navigateUp,
            onPlayMusic = {
                viewModel.playMusic()
                viewModel.startRecording()
            },
            onStopMusic = {
                viewModel.stopMusic()
                viewModel.stopRecording()
            },
            uiState = uiState
        )
    }
}

@Composable
fun EvaluationRecordScreen(
    onPressNextButton: () -> Unit,
    onPressBackButton: () -> Unit,
    onPlayMusic: () -> Unit,
    onStopMusic: () -> Unit,
    uiState: EvaluationRecordUiState,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
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


        Column(
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
                .verticalScroll(
                    state = scrollState
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for ((time, lyric) in uiState.songLyrics.entries.toList()) {
                val color = if(time == uiState.currentTimeStamp) MyVersionMain else White
                Text(
                    text = lyric,
                    color = color,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))
            }
        }


        MyVersionHorizontalDivider(
            modifier = commonModifier
        )

        Row(
            modifier = commonModifier.padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyVersionBasicIconButton(
                icon = DesignSystemR.drawable.ic_play_back,
                onClick = { /*TODO*/ },
                color = Grey200
            )

            Box(
                modifier = Modifier
                    .background(
                        color = Grey200,
                        shape = CircleShape
                    )
                    .wrapContentSize()
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                MyVersionBasicIconButton(
                    icon = DesignSystemR.drawable.ic_record_32,
                    onClick = onPlayMusic,
                    color = Color.Red
                )
            }

            MyVersionBasicIconButton(
                icon = DesignSystemR.drawable.ic_stop,
                onClick = onStopMusic,
                color = Grey200
            )
        }



        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
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
}

@Preview(showBackground = true, showSystemUi = true)
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