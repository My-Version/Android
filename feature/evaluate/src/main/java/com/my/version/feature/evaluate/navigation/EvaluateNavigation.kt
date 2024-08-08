package com.my.version.feature.evaluate.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.MainTabRoute
import com.my.version.feature.evaluate.EvaluateRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToEvaluate(navOptions: NavOptions) = navigate(Evaluate, navOptions)

fun NavGraphBuilder.evaluateScreen(
    modifier: Modifier
) {
    composable<Evaluate>{
        EvaluateRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object Evaluate: MainTabRoute