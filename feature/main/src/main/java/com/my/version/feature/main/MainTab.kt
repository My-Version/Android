package com.my.version.feature.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.my.version.core.common.navigation.MainTabRoute
import com.my.version.core.common.navigation.Route
import com.my.version.feature.cover.navigation.Cover
import com.my.version.feature.evaluate.navigation.Evaluation
import com.my.version.feature.home.navigation.Home

enum class MainTab(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    @StringRes val contentDescriptionId: Int = R.string.bottom_nav_content_description,
    val route: MainTabRoute
) {
    HOME(
        titleId = R.string.bottom_nav_home,
        iconId = R.drawable.ic_home_24,
        route = Home
    ),
    COVER(
        titleId = R.string.bottom_nav_cover,
        iconId = R.drawable.ic_cover_24,
        route = Cover
    ),
    EVALUATE(
        titleId = R.string.bottom_nav_evaluate,
        iconId = R.drawable.ic_music_24,
        route = Evaluation
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}