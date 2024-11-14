package com.my.version.feature.evaluate.upload.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.my.version.core.common.navigation.Route
import com.my.version.feature.evaluate.upload.EvaluationUploadRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluationUpload(
    navOptions: NavOptions? = null,
    filePath: String,
    coverId: Long
) = navigate(EvaluationUpload(filePath, coverId), navOptions)

fun NavGraphBuilder.evaluationUploadScreen(
    navigateToEvaluationMain: () -> Unit,
    modifier: Modifier
) {
    composable<EvaluationUpload> { backStackEntry ->
        val filePath = backStackEntry.toRoute<EvaluationUpload>().filePath
        val coverId = backStackEntry.toRoute<EvaluationUpload>().coverId

        EvaluationUploadRoute(
            music = "Ditto",
            artist = "NewJeans",
            modifier = modifier,
            onNavigateToHome = navigateToEvaluationMain,
            filePath = filePath,
            coverId = coverId
        )
    }
}

@Serializable
data class EvaluationUpload(
    val filePath: String = "",
    val coverId: Long = 0L
) : Route