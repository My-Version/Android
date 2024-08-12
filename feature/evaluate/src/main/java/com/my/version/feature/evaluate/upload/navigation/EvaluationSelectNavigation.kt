package com.my.version.feature.evaluate.upload.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.evaluate.upload.EvaluationUploadRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluationUpload(navOptions: NavOptions? = null) = navigate(EvaluationUpload, navOptions)

fun NavGraphBuilder.evaluationUploadScreen(
    modifier: Modifier
) {
    composable<EvaluationUpload>{
        EvaluationUploadRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object EvaluationUpload: Route