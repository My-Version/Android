package com.my.version.feature.auth.splash.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.auth.splash.SplashRoute
import kotlinx.serialization.Serializable


fun NavController.navigateToSplash(navOptions: NavOptions? = null) = navigate(Splash, navOptions)

fun NavGraphBuilder.splashScreen(
    modifier: Modifier,
    navigateToSignIn: (NavOptions) -> Unit = {},
    navigateToHome: (NavOptions) -> Unit = {}
) {
    val navOptions = NavOptions.Builder().setPopUpTo(
        route = Splash, inclusive = true
    ).build()

    composable<Splash> {
        SplashRoute(modifier = modifier,
            navigateToSignIn = { navigateToSignIn(navOptions) },
            navigateToHome = { navigateToHome(navOptions) })
    }
}

@Serializable
data object Splash : Route
