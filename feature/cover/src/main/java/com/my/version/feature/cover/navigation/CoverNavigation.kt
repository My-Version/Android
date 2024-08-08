package com.my.version.feature.cover.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.MainTabRoute
import com.my.version.feature.cover.CoverRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCover(navOptions: NavOptions) = navigate(Cover, navOptions)

fun NavGraphBuilder.coverScreen(
    modifier: Modifier
) {
    composable<Cover>{
        CoverRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object Cover: MainTabRoute