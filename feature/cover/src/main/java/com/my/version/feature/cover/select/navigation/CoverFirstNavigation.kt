package com.my.version.feature.cover.select.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.cover.select.CoverFirstRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCoverFirst(navOptions: NavOptions? = null) = navigate(CoverFirst, navOptions)

fun NavGraphBuilder.coverFirstScreen(
    modifier: Modifier
) {
    composable<CoverFirst>{
        CoverFirstRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object CoverFirst: Route