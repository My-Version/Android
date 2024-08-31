package com.my.version.feature.cover.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.dialog.MyVersionBasicDialog
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme

@Composable
fun UploadDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {
    MyVersionBasicDialog(
        onDismiss = onDismissRequest,
        onConfirm = onConfirmRequest,
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Confirm files")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UploadDialogPreview() {
    MyVersionTheme {
        Box(modifier = Modifier.background(color = MyVersionBackground)) {
            UploadDialog(
                onDismissRequest = {  },
                onConfirmRequest = {  }
            )
        }
    }
}