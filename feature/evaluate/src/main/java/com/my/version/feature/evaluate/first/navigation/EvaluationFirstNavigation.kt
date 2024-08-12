package com.my.version.feature.evaluate.first.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.evaluate.first.EvaluationFirstRoute
import com.my.version.feature.evaluate.main.navigation.Evaluation
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluationFirst(navOptions: NavOptions) = navigate(Evaluation, navOptions)

fun NavGraphBuilder.evaluationFirstScreen(
    modifier: Modifier
) {
    composable<EvaluationFirst>{
        EvaluationFirstRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object EvaluationFirst: Route