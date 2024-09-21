package com.my.version.feature.cover.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.my.version.core.common.watch.StopWatch
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.feature.cover.R
import com.my.version.feature.cover.component.OutlinedTextButton

@Composable
internal fun RecordDialog(
    onDismissRequest: (String) -> Unit,
    viewModel: RecordViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val stopWatch = remember { StopWatch() }

    LaunchedEffect(key1 = viewModel.sideEffect, key2 = lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when(sideEffect) {
                    is RecordSideEffect.NavigateUp -> onDismissRequest("")
                    is RecordSideEffect.StopRecord -> onDismissRequest(viewModel.getFilePath())
                }
            }
    }

    Dialog(
        onDismissRequest = viewModel::dismissDialog,
        properties = DialogProperties(
            dismissOnBackPress = !viewModel.isRecording,
            dismissOnClickOutside = !viewModel.isRecording
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 30.dp)
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = MyVersionBackground
                )
                .padding(horizontal = 20.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stopWatch.formattedTime,
                style = MyVersionTypography.displayLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
            ) {
                OutlinedTextButton(
                    onClick = {
                        viewModel.startRecording()
                        stopWatch.start()
                    },
                    text = stringResource(id = R.string.cover_dialog_button_start)
                )
                BasicSpacer(width = 10.dp)
                OutlinedTextButton(
                    onClick = {
                        viewModel.stopRecording()
                        stopWatch.reset()
                    },
                    text = stringResource(id = R.string.cover_dialog_button_stop)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordDialogPreview() {
    MyVersionTheme {
        RecordDialog(
            onDismissRequest = {}
        )
    }
}