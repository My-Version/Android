package com.my.version.feature.cover.select.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.cover.select.CoverSelectRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCoverSelect(navOptions: NavOptions? = null) = navigate(CoverSelect, navOptions)

fun NavGraphBuilder.coverSelectScreen(
    modifier: Modifier
) {
    composable<CoverSelect>{
        CoverSelectRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object CoverSelect: Route