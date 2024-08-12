package com.my.version.feature.cover.upload.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.cover.upload.CoverUploadRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCoverUpload(navOptions: NavOptions? = null) = navigate(CoverUpload, navOptions)

fun NavGraphBuilder.coverUploadScreen(
    modifier: Modifier
) {
    composable<CoverUpload>{
        CoverUploadRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object CoverUpload: Route