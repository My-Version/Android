package com.my.version.feature.evaluate.select.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.evaluate.select.EvaluationSelectRoute
import com.my.version.feature.evaluate.main.navigation.Evaluation
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluationSelect(navOptions: NavOptions? = null) = navigate(EvaluationSelect, navOptions)

fun NavGraphBuilder.evaluationSelectScreen(
    modifier: Modifier
) {
    composable<EvaluationSelect>{
        EvaluationSelectRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object EvaluationSelect: Route