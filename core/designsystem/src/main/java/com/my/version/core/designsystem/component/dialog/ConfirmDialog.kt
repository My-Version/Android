package com.my.version.core.designsystem.component.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.my.version.core.common.state.UiState
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionTypography

@Composable
fun ConfirmDialog(
    loadState: UiState<String>,
    text: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    onUploadRequest: () -> Unit,
    modifier: Modifier = Modifier,
    visibility: Boolean = false
) {
    if (visibility) {

        when (loadState) {
            is UiState.Empty -> {
                MyVersionBasicDialog(
                    onDismiss = onDismissRequest,
                    modifier = modifier.wrapContentHeight(),
                ) {
                    ConfirmView(
                        text = text,
                        onDismissRequest = onDismissRequest,
                        onConfirmRequest = onUploadRequest
                    )
                }
            }

            is UiState.Loading -> {
                MyVersionBasicDialog(
                    onDismiss = {},
                    modifier = modifier.wrapContentHeight(),
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    )
                ) {
                    UploadProcessView(
                        titleTextRes = R.string.confirm_dialog_uploading,
                        buttonTextRes = R.string.confirm_dialog_button_uploading,
                        buttonEnabled = false
                    )
                }
            }

            is UiState.Failure -> {
                MyVersionBasicDialog(
                    onDismiss = onDismissRequest,
                    modifier = modifier.wrapContentHeight(),
                ) {
                    UploadProcessView(
                        titleTextRes = R.string.confirm_dialog_upload_error,
                        buttonTextRes = R.string.confirm_dialog_button_retry,
                        onClick = onUploadRequest
                    )
                }
            }

            is UiState.Success -> {
                MyVersionBasicDialog(
                    onDismiss = onConfirmRequest,
                    modifier = modifier.wrapContentHeight(),
                ) {
                    UploadProcessView(
                        titleTextRes = R.string.confirm_dialog_upload_complete,
                        buttonTextRes = R.string.confirm_dialog_button_complete,
                        onClick = onConfirmRequest
                    )
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
                text = stringResource(R.string.confirm_dialog_confirm_cancel),
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
                text = stringResource(R.string.confirm_dialog_confirm_next),
                onClick = onConfirmRequest,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun UploadProcessView(
    @StringRes titleTextRes: Int,
    @StringRes buttonTextRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonEnabled: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(titleTextRes),
            style = MyVersionTypography.bodyLarge,
            color = Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 40.dp)
        )
        RectangleButton(
            isEnabled = buttonEnabled,
            textStyle = MyVersionTypography.bodyMedium,
            innerPadding = 10,
            cornerRadius = 5.dp,
            text = stringResource(buttonTextRes),
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UploadDialogPreview() {
    ConfirmView(
        text = "Are you sure you want to upload this file?",
        onDismissRequest = { },
        onConfirmRequest = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun LoadingDialogPreview() {
    UploadProcessView(
        titleTextRes = R.string.confirm_dialog_uploading,
        buttonTextRes = R.string.confirm_dialog_button_uploading,
        buttonEnabled = false
    )
}

@Preview(showBackground = true)
@Composable
private fun CompleteDialogPreview() {
    UploadProcessView(
        titleTextRes = R.string.confirm_dialog_upload_error,
        buttonTextRes = R.string.confirm_dialog_button_retry,
        onClick = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun FailedDialogPreview() {
    UploadProcessView(
        titleTextRes = R.string.confirm_dialog_upload_complete,
        buttonTextRes = R.string.confirm_dialog_button_complete,
        onClick = { }
    )
}