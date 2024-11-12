package com.my.version.feature.auth.signin.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.auth.signin.SignInRoute
import kotlinx.serialization.Serializable


fun NavController.navigateToSignIn(navOptions: NavOptions? = null) = navigate(SignIn, navOptions)

fun NavGraphBuilder.signInScreen(
    modifier: Modifier,
    navigateToSignUp: () -> Unit = {},
    navigateToHome: (navOptions: NavOptions) -> Unit = {}
) {
    val navOptions = NavOptions.Builder().setPopUpTo(
        route = SignIn, inclusive = true
    ).build()

    composable<SignIn> {
        SignInRoute(
            modifier = modifier,
            navigateToSignUp = navigateToSignUp,
            navigateToHome = { navigateToHome(navOptions) }
        )
    }
}

@Serializable
data object SignIn : Route
