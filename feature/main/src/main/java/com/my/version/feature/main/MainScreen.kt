package com.my.version.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.my.version.feature.auth.signin.navigation.signInScreen
import com.my.version.feature.auth.signup.navigation.signUpScreen
import com.my.version.feature.cover.navigation.coverScreen
import com.my.version.feature.evaluate.navigation.evaluateScreen
import com.my.version.feature.home.navigation.homeScreen

@Composable
fun MainScreen(
    mainNavigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            MainNavHost(
                mainNavigator = mainNavigator
            )
        }
    }
}

@Composable
fun MainNavHost(
    mainNavigator: MainNavigator
) {
    NavHost(
        navController = mainNavigator.navController,
        startDestination = mainNavigator.startDestination
    ) {
        homeScreen()
        coverScreen()
        evaluateScreen()
        signInScreen()
        signUpScreen()
    }
}