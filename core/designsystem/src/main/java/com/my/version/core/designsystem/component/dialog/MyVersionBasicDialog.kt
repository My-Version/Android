package com.my.version.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White

@Composable
fun MyVersionBasicDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = modifier
                .background(color = White)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = stringResource(id = R.string.btn_close)
                )
            }

            content()

            RectangleButton(
                isEnabled = true,
                text = stringResource(id = R.string.btn_next),
                textStyle = MaterialTheme.typography.titleLarge, 
                innerPadding = 8,
                cornerRadius = 5.dp,
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true) 
@Composable
private fun MyVersionBasicDialogPreview() {
    MyVersionTheme {
        Box(
            modifier = Modifier.background(MyVersionBackground)
        ) {
            MyVersionBasicDialog(
                onDismiss = {  },
                onConfirm = {  },
                content = {  })
        }
    }
}