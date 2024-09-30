package com.my.version.feature.evaluate.result.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.my.version.core.common.navigation.Route
import com.my.version.feature.evaluate.record.EvaluationRecordRoute
import com.my.version.feature.evaluate.result.EvaluationResultRoute
import com.my.version.feature.evaluate.upload.navigation.EvaluationUpload
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluationResult(evaluationId: String, navOptions: NavOptions? = null) =
    navigate(EvaluationResult(evaluationId), navOptions)

fun NavGraphBuilder.evaluationResultScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<EvaluationResult> {backStackEntry ->
        val evaluationId = backStackEntry.toRoute<EvaluationResult>().evaluationId
        EvaluationResultRoute(
            modifier = modifier,
            navigateUp = navigateUp,
            evaluationId = evaluationId,
        )
    }
}

@Serializable
data class EvaluationResult(
    val evaluationId: String = ""
) : Route