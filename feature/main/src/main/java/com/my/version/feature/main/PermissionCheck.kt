package com.my.version.feature.main

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun PermissionChecker() {
    val activity = LocalContext.current as? Activity
    val permissions = getAppropriatePermissions()
    val permissionState = rememberMultiplePermissionsState(
        permissions = getAppropriatePermissions().toList()
    )

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMap ->

        val granted: Boolean = permissionMap.values.reduce { acc, next ->
            acc && next
        }

        if (granted) {
            Timber.tag("PermissionChecker").d("All Permission are granted")
        } else {
            activity?.finish()
        }
    }

    LaunchedEffect(permissionState) {
        if (permissionState.allPermissionsGranted) {
            Timber.tag("PermissionChecker").d("All Permission are already granted")
        } else {
            Timber.tag("PermissionChecker").d("Permission must be granted")
            requestPermissionLauncher.launch(permissions)
        }
    }
}

private fun getAppropriatePermissions(): Array<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        arrayOf(
            Manifest.permission.RECORD_AUDIO
        )
    }