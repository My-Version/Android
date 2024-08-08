package com.my.version.feature.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import com.my.version.core.designsystem.theme.Purple80
import com.my.version.feature.auth.signin.navigation.signInScreen
import com.my.version.feature.auth.signup.navigation.signUpScreen
import com.my.version.feature.cover.navigation.coverScreen
import com.my.version.feature.evaluate.navigation.evaluateScreen
import com.my.version.feature.home.navigation.homeScreen
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
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            MainNavHost(
                navigator = navigator
            )
        }
    }
}

@Composable
fun MainNavHost(
    navigator: MainNavigator
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination
    ) {
        homeScreen()
        coverScreen()
        evaluateScreen()
        signInScreen()
        signUpScreen()
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
        enter = expandVertically(expandFrom = Alignment.Top) { 20 },
        exit = shrinkVertically(animationSpec = tween()) { fullHeight ->
            fullHeight / 2
        },
    ) {
        NavigationBar(containerColor = Purple80) {
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
                            fontSize = 9.sp
                        )
                    },
                    colors = androidx.compose.material3.NavigationBarItemDefaults
                        .colors(
                            selectedIconColor = White,
                            selectedTextColor = White,
                            unselectedIconColor = LightGray,
                            unselectedTextColor = LightGray,
                            indicatorColor = White
                        )

                )
            }
        }
    }
}