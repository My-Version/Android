package com.my.version.feature.evaluate.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.core.designsystem.theme.White
import com.my.version.feature.evaluate.result.component.ExpandableAudioItem

@Composable
fun EvaluationResultRoute(
    evaluationId: String,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
) {


    EvaluationResultScreen(
        navigateUp = navigateUp
    )
}

@Composable
private fun EvaluationResultScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = navigateUp,
            title = "Evaluation Result"
        )

        SuccessScreen()
    }
}

@Composable
private fun SuccessScreen(
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicSpacer(height = 24.dp)
        Text(
            text = "Similarity",
            style = MyVersionTypography.titleMedium
        )
        BasicSpacer(height = 32.dp)
        Text(
            text = "96%",
            style = MyVersionTypography.headlineLarge,
            color = MyVersionMain
        )
        BasicSpacer(height = 32.dp)
        Text(
            text = "Very Similar to AI cover",
            style = MyVersionTypography.bodyLarge,
            color = Grey350
        )
        BasicSpacer(height = 32.dp)
        HorizontalDivider(
            thickness = 1.dp,
            color = Grey200
        )
        BasicSpacer(height = 32.dp)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            ExpandableAudioItem(
                title = "title",
                subTitle = "subtitle",
                progress = 0.4f,
                isExpanded = isExpanded,
                backgroundColor = Grey200,
                onClickItem = { isExpanded = !isExpanded})
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
            navigateUp = {}
        )
    }
}