package com.my.version.feature.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.my.version.core.common.navigation.Route
import com.my.version.core.designsystem.component.topappbar.LogoTopAppBar
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.Grey300
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.White
import com.my.version.feature.auth.signin.navigation.signInScreen
import com.my.version.feature.auth.signup.navigation.signUpScreen
import com.my.version.feature.cover.navigation.coverFirstScreen
import com.my.version.feature.cover.navigation.coverScreen
import com.my.version.feature.cover.navigation.coverSecondScreen
import com.my.version.feature.evaluate.main.navigation.evaluationScreen
import com.my.version.feature.evaluate.record.navigation.evaluationRecordScreen
import com.my.version.feature.evaluate.select.navigation.evaluationSelectScreen
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
        val modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())

        MyVersionNavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            modifier = modifier
        )
    }
}

@Composable
fun MyVersionNavHost(
    navController: NavHostController,
    startDestination: Route,
    modifier: Modifier = Modifier
) {
    val noBottomBarModifier = Modifier
        .systemBarsPadding()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(
            modifier = modifier
        )
        coverScreen(
            modifier = modifier
        )
        coverFirstScreen(
            modifier = noBottomBarModifier
        )
        coverSecondScreen(
            modifier = noBottomBarModifier
        )
        evaluationScreen(
            modifier = modifier
        )
        evaluationSelectScreen(
            modifier = noBottomBarModifier
        )
        evaluationRecordScreen(
            modifier = noBottomBarModifier
        )
        signInScreen(
            modifier = noBottomBarModifier,
            onButtonClick = { navController.navigateToHome() }
        )
        signUpScreen(
            modifier = noBottomBarModifier
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
                            stringResource(id = itemType.titleId),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    colors = androidx.compose.material3.NavigationBarItemDefaults
                        .colors(
                            selectedIconColor = White,
                            selectedTextColor = White,
                            unselectedIconColor = Grey400,
                            unselectedTextColor = Grey400,
                            indicatorColor = MyVersionMain
                        )
                )
            }
        }
    }
}