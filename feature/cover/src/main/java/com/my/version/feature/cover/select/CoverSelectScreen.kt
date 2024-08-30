package com.my.version.feature.cover.select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.divider.TitleWithDivider
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.domain.entity.MusicAudioFile
import com.my.version.feature.cover.R

@Composable
fun CoverSelectRoute(
    navigateUp: () -> Unit,
    navigateToUpload: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CoverSelectViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    CoverSelectScreen(
        modifier = modifier,
        musicList = uiState.musicList,
        selectedIndex = uiState.selected,
        onItemClicked = { selectedIndex ->
            viewModel.updateSelectedIndex(selectedIndex)
            viewModel.playMusic(selectedIndex)
        },
        onNavigateUp = navigateUp,
        onNextClicked = {
            viewModel.releaseMusicPlayer()
            navigateToUpload()
        }
    )
}

@Composable
fun CoverSelectScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    musicList: List<MusicAudioFile>,
    onItemClicked: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    onNextClicked: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = onNavigateUp,
            title = stringResource(id = R.string.cover_topbar_selection)
        )

        TitleWithDivider(
            text = stringResource(id = R.string.cover_on_boarding_title1),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            items(musicList) { cover ->
                val currentIndex = musicList.indexOf(cover)
                val selected = selectedIndex == currentIndex

                MyVersionVerticalItem(
                    itemType = VerticalItemType.MUSIC,
                    iconColor = if(selected) MyVersionMain else Black,
                    onClick = {
                        onItemClicked(currentIndex)
                    },
                    title = cover.title,
                    subTitle = cover.artist
                )
                if (musicList.last() != cover) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Box(
            modifier = Modifier.wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            RectangleButton(
                isEnabled = selectedIndex != -1,
                text = "Next",
                textStyle = MaterialTheme.typography.titleMedium,
                innerPadding = 20,
                modifier = Modifier.fillMaxWidth(),
                onClick = onNextClicked
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoverFirstScreenPreview() {
    MyVersionTheme {
        Box(
            modifier = Modifier.background(color = MyVersionBackground)
        ) {
            CoverSelectScreen(
                musicList = emptyList(),
                selectedIndex = 1,
                onItemClicked = {},
                onNavigateUp = {},
                onNextClicked = {}
            )
        }
    }
}