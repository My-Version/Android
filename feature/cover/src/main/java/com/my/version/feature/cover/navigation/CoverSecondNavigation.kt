package com.my.version.feature.cover.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.cover.CoverFirstRoute
import com.my.version.feature.cover.CoverSecondRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCoverSecond(navOptions: NavOptions? = null) = navigate(CoverSecond, navOptions)

fun NavGraphBuilder.coverSecondScreen(
    modifier: Modifier
) {
    composable<CoverSecond>{
        CoverSecondRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object CoverSecond: Route