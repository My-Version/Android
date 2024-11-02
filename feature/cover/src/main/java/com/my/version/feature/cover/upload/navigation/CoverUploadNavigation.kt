package com.my.version.feature.cover.upload.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.cover.upload.CoverUploadRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCoverUpload(musicName: String, navOptions: NavOptions? = null) =
    navigate(CoverUpload(musicName), navOptions)

fun NavGraphBuilder.coverUploadScreen(
    modifier: Modifier,
    navigateUp: () -> Unit,
    onUploadComplete: () -> Unit
) {
    composable<CoverUpload> { backStackEntry ->
        val musicName = backStackEntry.arguments?.getString("musicName") ?: ""

        CoverUploadRoute(
            selectedMusicName = musicName,
            modifier = modifier,
            onNavigateUp = navigateUp,
            onUploadComplete = onUploadComplete
        )
    }
}

@Serializable
data class CoverUpload(
    val musicName: String
) : Route