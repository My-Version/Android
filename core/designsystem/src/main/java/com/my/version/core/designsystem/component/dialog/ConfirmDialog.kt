package com.my.version.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.MyVersionTypography

@Composable
fun ConfirmDialog(
    text: String = "Confirm files",
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {
    MyVersionBasicDialog(
        onDismiss = onDismissRequest,
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = MyVersionTypography.bodyLarge
                )
            }

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
}

@Preview(showBackground = true)
@Composable
private fun UploadDialogPreview() {
    MyVersionTheme {
        Box(modifier = Modifier.background(color = MyVersionBackground)) {
            ConfirmDialog(
                onDismissRequest = { },
                onConfirmRequest = { }
            )
        }
    }
}