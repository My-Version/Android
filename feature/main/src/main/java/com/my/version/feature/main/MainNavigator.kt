package com.my.version.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.my.version.feature.cover.navigation.CoverFirst
import com.my.version.feature.cover.navigation.CoverSecond
import com.my.version.feature.cover.navigation.navigateToCover
import com.my.version.feature.evaluate.main.navigation.Evaluation
import com.my.version.feature.evaluate.select.navigation.EvaluationSelect
import com.my.version.feature.evaluate.main.navigation.navigateToEvaluation
import com.my.version.feature.evaluate.record.navigation.EvaluationRecord
import com.my.version.feature.home.navigation.Home
import com.my.version.feature.home.navigation.navigateToHome

class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Evaluation

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions)
            MainTab.COVER -> navController.navigateToCover(navOptions)
            MainTab.EVALUATE -> navController.navigateToEvaluation(navOptions)
        }
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    @Composable
    fun showBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}