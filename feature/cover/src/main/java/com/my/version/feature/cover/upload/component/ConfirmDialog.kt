package com.my.version.feature.cover.upload.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.common.state.UiState
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.dialog.MyVersionBasicDialog
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.feature.cover.upload.state.ConfirmDialogUiState

@Composable
fun ConfirmDialog(
    dialogState: ConfirmDialogUiState,
    text: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    onUploadRequest: () -> Unit = {},
    modifier: Modifier = Modifier,
    visibility: Boolean = false
) {
    if (visibility) {
        MyVersionBasicDialog(
            onDismiss = onDismissRequest,
            modifier = modifier.height(300.dp)
        ) {
            when(dialogState.loadState) {
                is UiState.Empty -> {
                    ConfirmView(
                        text = text,
                        onDismissRequest = onDismissRequest,
                        onConfirmRequest = onUploadRequest
                    )
                }
                is UiState.Loading -> {
                    LoadingView()
                }
                is UiState.Failure -> {
                    UploadFailView(
                        onRetryRequest = onUploadRequest
                    )
                }
                is UiState.Success -> {
                    UploadCompleteView(onConfirmRequest = onConfirmRequest)
                }
            }
        }
    }
}

@Composable
private fun ConfirmView(
    text: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MyVersionTypography.bodyLarge,
            color = Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 40.dp)
        )


        Row {
            RectangleButton(
                isEnabled = true,
                textStyle = MyVersionTypography.bodyMedium,
                innerPadding = 10,
                cornerRadius = 5.dp,
                text = "Cancel",
                onClick = onDismissRequest,
                modifier = Modifier.weight(1f),
                backgroundColor = Grey400
            )
            BasicSpacer(width = 10.dp)
            RectangleButton(
                isEnabled = true,
                textStyle = MyVersionTypography.bodyMedium,
                innerPadding = 10,
                cornerRadius = 5.dp,
                text = "Confirm",
                onClick = onConfirmRequest,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun LoadingView(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text("Uploading...")
    }
}

@Composable
private fun UploadFailView(
    modifier: Modifier = Modifier,
    onRetryRequest: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Text("Upload Failed..")
        Button(onClick = onRetryRequest) {
            Text("Retry")
        }
    }
}

@Composable
private fun UploadCompleteView(
    modifier: Modifier = Modifier,
    onConfirmRequest: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Text("Upload Complete!")
        Button(onClick = onConfirmRequest) {
            Text("Back to Home")
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun UploadDialogPreview() {
    ConfirmDialog(
        text = "Are you sure you want to delete this file?",
        onDismissRequest = { },
        onConfirmRequest = { },
        dialogState = ConfirmDialogUiState(),
    )
}