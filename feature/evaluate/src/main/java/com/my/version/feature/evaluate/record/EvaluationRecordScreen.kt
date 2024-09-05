package com.my.version.feature.evaluate.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.my.version.core.designsystem.component.button.MyVersionBasicIconButton
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.divider.MyVersionHorizontalDivider
import com.my.version.core.designsystem.component.divider.TitleWithDivider
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White
import com.my.version.feature.evaluate.R
import com.my.version.core.designsystem.R as DesignSystemR

/**
 * 로직
 * 1. 녹음 버튼을 누른다
 * 2. 카운트다운 3과 함께 녹음 및 음악 재생을 실행한다.
 */

@Composable
fun EvaluationRecordRoute(
    modifier: Modifier = Modifier,
    viewModel: EvaluationRecordViewModel = hiltViewModel()
) {
    EvaluationRecordScreen(
        modifier = modifier,
        onPlayMusic = {
            viewModel.startRecording()
            //viewModel.playMusic()
        },
        onStopMusic = {
            viewModel.stopRecording()
            //viewModel.stopMusic()
        },
        lyrics = listOf(
            "sdasdasdasd",
            "asdsadasdsad",
            "fsdabfahdfadsf",
            "dafsdfafadf",
            "safdsfsgfsdg",
            "fsdabfahdfadsf",
            "dafsdfafadf",
            "safdsfsgfsdg",
            "fsdabfahdfadsf",
            "dafsdfafadf",
            "safdsfsgfsdg",
            "fsdabfahdfadsf",
            "dafsdfafadf",
            "safdsfsgfsdg",
            "fsdabfahdfadsf",
            "dafsdfafadf",
            "safdsfsgfsdg",
            "fsdabfahdfadsf",
            "dafsdfafadf",
            "safdsfsgfsdg"
        )
    )
}

@Composable
fun EvaluationRecordScreen(
    onPlayMusic: () -> Unit,
    onStopMusic: () -> Unit,
    lyrics: List<String>,
    modifier: Modifier = Modifier,
) {
    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)

    Column(
        modifier = modifier
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = { },
            title = stringResource(id = R.string.evaluation_topbar_record)
        )

        TitleWithDivider(
            text = stringResource(id = R.string.evaluation_on_boarding_title2),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = commonModifier
        )

        LazyColumn(
            modifier = commonModifier
                .height(450.dp),
            contentPadding = PaddingValues(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(lyrics) { line ->
                Text(
                    text = line,
                    color = White,
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
                isEnabled = false,
                text = "Next",
                textStyle = MaterialTheme.typography.titleMedium,
                innerPadding = 20,
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
            lyrics = listOf(
                "sdasdasdasd",
                "asdsadasdsad",
                "fsdabfahdfadsf",
                "dafsdfafadf",
                "safdsfsgfsdg"
            ),
            onPlayMusic = {},
            onStopMusic = {}
        )
    }
}