package com.my.version.feature.cover.main.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.MainTabRoute
import com.my.version.feature.cover.main.CoverRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCover(navOptions: NavOptions? = null) = navigate(Cover, navOptions)

fun NavGraphBuilder.coverScreen(
    modifier: Modifier,
    navigateToSelect: () -> Unit,

) {
    composable<Cover>{
        CoverRoute(
            modifier = modifier,
            navigateToSelect = navigateToSelect
        )
    }
}

@Serializable
data object Cover: MainTabRoute