package com.my.version.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.MainTabRoute
import com.my.version.feature.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(Home, navOptions)

fun NavGraphBuilder.homeScreen() {
    composable<Home>{
        HomeRoute()
    }
}

@Serializable
data object Home: MainTabRoute