package com.my.version.feature.cover.record

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.my.version.core.designsystem.theme.MyVersionTheme
import java.io.File

@RequiresApi(Build.VERSION_CODES.S)
@Composable
internal fun RecordDialog(
    onDismissRequest: () -> Unit,
    viewModel: RecordViewModel = hiltViewModel()
) {

    Dialog(
        onDismissRequest = {
            viewModel.stopRecording()
            onDismissRequest()
        }
    ) {
        Row {
            Button(
                onClick = {
                    viewModel.startRecording()
                }
            ) {
                Text(text = "Record")
            }
            Button(
                onClick = {
                    viewModel.stopRecording()
                }
            ) {
                Text(text = "Stop")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun initMediaRecorder(context: Context, file: File): MediaRecorder {
    return MediaRecorder(context).apply {
        setAudioSource(MediaRecorder.AudioSource.MIC)
        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
        setOutputFile(file.absolutePath)
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true)
@Composable
private fun RecordDialogPreview() {
    MyVersionTheme {
        RecordDialog(
            onDismissRequest = {}
        )
    }
}