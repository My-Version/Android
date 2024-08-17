package com.my.version.feature.main

import android.Manifest
import android.app.Activity
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
    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )
    val permissionState = rememberMultiplePermissionsState(
        permissions = permissions.toList()
    )

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMap ->
        val granted: Boolean = permissionMap.values.reduce { acc, next ->
            acc && next
        }

        if(granted) {
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