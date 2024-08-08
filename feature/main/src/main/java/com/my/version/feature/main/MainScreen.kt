package com.my.version.feature.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.my.version.core.common.extension.noScaffoldPadding
import com.my.version.core.common.navigation.Route
import com.my.version.core.designsystem.theme.Purple40
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
        val scaffoldModifier = Modifier.padding(paddingValues)
        val insetModifier = Modifier.noScaffoldPadding()

        MainNavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            scaffoldModifier = scaffoldModifier,
            insetModifier = insetModifier
        )
    }
}

@Composable
fun MainNavHost(
    scaffoldModifier: Modifier,
    insetModifier: Modifier,
    navController: NavHostController,
    startDestination: Route,
) {
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
            modifier = insetModifier,
            onButtonClick = { navController.navigateToHome() }
        )
        signUpScreen(
            modifier = insetModifier
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
            containerColor = Purple40
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
                            fontSize = 12.sp
                        )
                    },
                    colors = androidx.compose.material3.NavigationBarItemDefaults
                        .colors(
                            selectedIconColor = White,
                            selectedTextColor = White,
                            unselectedIconColor = LightGray,
                            unselectedTextColor = LightGray,
                            indicatorColor = Purple40
                        )

                )
            }
        }
    }
}