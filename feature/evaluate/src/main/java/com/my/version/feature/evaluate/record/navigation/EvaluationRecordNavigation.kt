package com.my.version.feature.evaluate.record.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.evaluate.select.EvaluationSelectRoute
import com.my.version.feature.evaluate.main.navigation.Evaluation
import com.my.version.feature.evaluate.record.EvaluationRecordRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluationRecord(navOptions: NavOptions? = null) = navigate(EvaluationRecord, navOptions)

fun NavGraphBuilder.evaluationRecordScreen(
    navigateUp: () -> Unit,
    navigateToEvaluationUpload: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    composable<EvaluationRecord>{
        EvaluationRecordRoute(
            modifier = modifier,
            navigateUp = navigateUp,
            navigateToEvaluationUpload = navigateToEvaluationUpload,
        )
    }
}

@Serializable
data object EvaluationRecord: Route