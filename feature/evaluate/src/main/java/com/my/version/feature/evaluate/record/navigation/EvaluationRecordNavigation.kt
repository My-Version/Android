package com.my.version.feature.evaluate.record.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.core.domain.entity.CoverAudio
import com.my.version.feature.evaluate.record.EvaluationRecordRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluationRecord(
    navOptions: NavOptions? = null,
    coverAudio: CoverAudio
) = navigate(
    EvaluationRecord(
        music = coverAudio.music,
        artist = coverAudio.artist,
        coverId = coverAudio.coverId
    ), navOptions
)

fun NavGraphBuilder.evaluationRecordScreen(
    navigateUp: () -> Unit,
    navigateToEvaluationUpload: (String, Long) -> Unit,
    modifier: Modifier = Modifier
) {
    composable<EvaluationRecord> { backStackEntry ->
        val music = backStackEntry.arguments?.getString("music").orEmpty()
        val artist = backStackEntry.arguments?.getString("artist").orEmpty()
        val coverId = backStackEntry.arguments?.getLong("coverId") ?: 0

        EvaluationRecordRoute(
            modifier = modifier,
            music = music,
            artist = artist,
            coverId = coverId,
            navigateUp = navigateUp,
            navigateToEvaluationUpload = navigateToEvaluationUpload,
        )
    }
}

@Serializable
data class EvaluationRecord(
    val music: String = "",
    val artist: String = "",
    val coverId: Long = 0
) : Route