package com.my.version.feature.auth.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.my.version.core.common.navigation.Route
import com.my.version.feature.auth.signin.SignInRoute
import com.my.version.feature.auth.signup.SignUpRoute
import kotlinx.serialization.Serializable


fun NavController.navigateToSignUp(navOptions: NavOptions) = navigate(SignUp, navOptions)

fun NavGraphBuilder.signUpScreen() {
    composable<SignUp>{
        SignUpRoute()
    }
}

@Serializable
data object SignUp: Route
