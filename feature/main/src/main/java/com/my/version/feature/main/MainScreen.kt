package com.my.version.feature.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.my.version.core.common.extension.noScaffoldPadding
import com.my.version.core.common.navigation.Route
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.feature.auth.signin.navigation.signInScreen
import com.my.version.feature.auth.signup.navigation.signUpScreen
import com.my.version.feature.cover.navigation.coverScreen
import com.my.version.feature.evaluate.navigation.evaluateScreen
import com.my.version.feature.home.navigation.homeScreen
import com.my.version.feature.home.navigation.navigateToHome
import com.terning.core.util.NoRippleInteractionSource

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold(
        bottomBar = {
            MainBottomBar(
                isVisible = navigator.showBottomBar(),
                tabs = MainTab.entries.toList(),
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate
            )
        }
    ) { paddingValues ->
        MainNavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun MainNavHost(
    paddingValues: PaddingValues,
    navController: NavHostController,
    startDestination: Route,
) {
    val scaffoldModifier = Modifier.padding(paddingValues)
    val noScaffoldModifier = Modifier.noScaffoldPadding()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(
            modifier = scaffoldModifier
        )
        coverScreen(
            modifier = scaffoldModifier
        )
        evaluateScreen(
            modifier = scaffoldModifier
        )
        signInScreen(
            modifier = noScaffoldModifier,
            onButtonClick = { navController.navigateToHome() }
        )
        signUpScreen(
            modifier = noScaffoldModifier
        )
    }
}

@Composable
private fun MainBottomBar(
    isVisible: Boolean,
    tabs: List<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        NavigationBar(
            containerColor = MyVersionMain
        ) {
            tabs.forEach { itemType ->
                NavigationBarItem(
                    interactionSource = NoRippleInteractionSource,
                    selected = currentTab == itemType,
                    onClick = {
                        onTabSelected(itemType)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = (itemType.iconId)),
                            contentDescription = stringResource(id = itemType.contentDescriptionId)
                        )
                    },
                    label = {
                        Text(
                            stringResource(id = itemType.contentDescriptionId),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    colors = androidx.compose.material3.NavigationBarItemDefaults
                        .colors(
                            selectedIconColor = White,
                            selectedTextColor = White,
                            unselectedIconColor = LightGray,
                            unselectedTextColor = LightGray,
                            indicatorColor = MyVersionMain
                        )

                )
            }
        }
    }
}