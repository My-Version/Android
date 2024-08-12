package com.my.version.feature.evaluate.main.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.MainTabRoute
import com.my.version.feature.evaluate.main.EvaluationRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluation(navOptions: NavOptions) = navigate(Evaluation, navOptions)

fun NavGraphBuilder.evaluationScreen(
    modifier: Modifier
) {
    composable<Evaluation>{
        EvaluationRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object Evaluation: MainTabRoute