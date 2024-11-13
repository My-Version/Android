package com.my.version.feature.cover.upload.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.cover.upload.CoverUploadRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCoverUpload(
    artist: String,
    music: String,
    navOptions: NavOptions? = null
) = navigate(CoverUpload(artist = artist, music = music), navOptions)

fun NavGraphBuilder.coverUploadScreen(
    modifier: Modifier,
    navigateUp: () -> Unit,
    onUploadComplete: () -> Unit
) {
    composable<CoverUpload> { backStackEntry ->
        val music = backStackEntry.arguments?.getString("music").orEmpty()
        val artist = backStackEntry.arguments?.getString("artist").orEmpty()

        CoverUploadRoute(
            selectedMusicArtist = artist,
            selectedMusic = music,
            modifier = modifier,
            onNavigateUp = navigateUp,
            onUploadComplete = onUploadComplete
        )
    }
}

@Serializable
data class CoverUpload(
    val artist: String,
    val music: String
) : Route