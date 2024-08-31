package com.my.version.feature.cover.upload.component

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun uploadResultLauncher(
    onResultOk: (Uri?) -> Unit = {},
    onResultCanceled: () -> Unit = {}
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
) { result ->
    when(result.resultCode) {
        RESULT_OK -> onResultOk(result.data?.data)
        RESULT_CANCELED -> onResultCanceled()
    }
}