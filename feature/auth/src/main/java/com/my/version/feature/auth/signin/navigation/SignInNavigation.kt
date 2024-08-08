package com.my.version.feature.auth.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.auth.signin.SignInRoute
import kotlinx.serialization.Serializable


fun NavController.navigateToSignIn(navOptions: NavOptions) = navigate(SignIn, navOptions)

fun NavGraphBuilder.signInScreen() {
    composable<SignIn>{
        SignInRoute()
    }
}

@Serializable
data object SignIn: Route