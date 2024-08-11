package com.my.version.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.feature.home.component.HomeHorizontalList
import com.my.version.feature.home.component.coverToComposableList
import com.my.version.feature.home.component.evaluationToComposableList
import com.my.version.feature.home.component.musicToComposableList


@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(modifier = modifier)
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier
) {
    SuccessScreen(
        musicList = tempList1,
        coverList = tempList1,
        evaluateList = tempList1,
        modifier = modifier
    )
}

@Composable
private fun SuccessScreen(
    musicList: List<TempItem>,
    coverList: List<TempItem>,
    evaluateList: List<TempItem>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        HomeHorizontalList(
            title = stringResource(id = R.string.home_heading_music),
            composableList = musicToComposableList(musicList = musicList),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))
        HomeHorizontalList(
            title = stringResource(id = R.string.home_heading_cover),
            composableList = coverToComposableList(coverList = coverList),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))
        HomeHorizontalList(
            title = stringResource(id = R.string.home_heading_evaluation),
            composableList = evaluationToComposableList(evaluationList = evaluateList),
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MyVersionTheme {
        Box(
            modifier = Modifier
                .background(color = MyVersionBackground)
        ) {
            HomeScreen(
                modifier = Modifier
            )
        }
    }
}