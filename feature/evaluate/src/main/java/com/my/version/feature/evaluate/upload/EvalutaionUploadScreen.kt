package com.my.version.feature.evaluate.upload

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
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

@Composable
fun EvaluationUploadRoute(
    modifier: Modifier = Modifier,
    viewModel: EvaluationUploadViewModel = hiltViewModel()
) {
    EvaluationUploadScreen(
        modifier = modifier
    )
}

@Composable
private fun EvaluationUploadScreen(
    modifier: Modifier = Modifier,
    lyrics: List<String> = emptyList()
) {
    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = { /*TODO*/ },
            title = stringResource(id = R.string.evaluation_topbar_upload)
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

        LinearProgressIndicator(
            progress = { 0.3f },
            modifier = commonModifier
                .padding(vertical = 30.dp)
                .height(7.dp),
            strokeCap = StrokeCap.Round
        )

        Row(
            modifier = commonModifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyVersionBasicIconButton(
                icon = DesignSystemR.drawable.ic_close,
                onClick = { /*TODO*/ },
                color = Grey200
            )

            MyVersionBasicIconButton(
                icon = DesignSystemR.drawable.ic_play,
                onClick = { /*TODO*/ },
                color = Grey200,
                modifier = Modifier.size(32.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            RectangleButton(
                isEnabled = false,
                text = stringResource(id = DesignSystemR.string.btn_upload_capital),
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
        EvaluationUploadScreen(
            modifier = Modifier.background(MyVersionBackground),
            lyrics = listOf(
                "sdasdasdasd",
                "asdsadasdsad",
                "fsdabfahdfadsf",
                "dafsdfafadf",
                "safdsfsgfsdg"
            )
        )
    }
}