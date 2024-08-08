package com.my.version.feature.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.MainTabRoute
import com.my.version.feature.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(Home, navOptions)

fun NavGraphBuilder.homeScreen(
    modifier: Modifier
) {
    composable<Home>{
        HomeRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object Home: MainTabRoute