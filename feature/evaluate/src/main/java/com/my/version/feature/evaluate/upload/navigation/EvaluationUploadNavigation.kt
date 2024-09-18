package com.my.version.feature.evaluate.upload.navigation

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.my.version.core.common.navigation.Route
import com.my.version.feature.evaluate.upload.EvaluationUploadRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluationUpload(navOptions: NavOptions? = null, filePath: String) = navigate(EvaluationUpload(filePath), navOptions)

fun NavGraphBuilder.evaluationUploadScreen(
    modifier: Modifier
) {
    composable<EvaluationUpload>{ backStackEntry ->
        val filePath = backStackEntry.toRoute<EvaluationUpload>().filePath
        EvaluationUploadRoute(
            modifier = modifier,
            filePath = filePath
        )
    }
}

@Serializable
data class EvaluationUpload(
    val filePath: String = ""
): Route