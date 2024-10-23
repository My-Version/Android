package com.my.version.feature.auth.signup.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.auth.signup.SignUpRoute
import kotlinx.serialization.Serializable


fun NavController.navigateToSignUp(navOptions: NavOptions? = null) = navigate(SignUp, navOptions)

fun NavGraphBuilder.signUpScreen(
    modifier: Modifier
) {
    composable<SignUp> {
        SignUpRoute(
            modifier = modifier
        )
    }
}

@Serializable
data object SignUp : Route
