package com.my.version.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.my.version.core.common.musicplayer.StreamMediaPlayer
import com.my.version.core.common.state.UiState
import com.my.version.core.designsystem.component.bottomsheet.SortingBottomSheet
import com.my.version.core.designsystem.component.box.AudioPlayBox
import com.my.version.core.designsystem.component.button.SortingButton
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.component.topappbar.LogoTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.MyVersionSub1
import com.my.version.core.designsystem.theme.White
import com.my.version.core.designsystem.type.SortBy
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.domain.entity.MusicAudioFile
import com.my.version.feature.home.state.HomeUiState
import timber.log.Timber
import com.my.version.core.designsystem.R as DesignSystemR

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val mediaPlayer = StreamMediaPlayer(context)

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is HomeSideEffect.StartMusic -> {
                        Timber.tag("StreamMusic").d(sideEffect.uri.toString())
                        with(mediaPlayer) {
                            endMediaPlayer()
                            prepareMediaPlayer(sideEffect.uri)
                        }
                    }

                    is HomeSideEffect.PlayMusic -> {
                        mediaPlayer.playMediaPlayer()
                    }

                    is HomeSideEffect.PauseMusic -> {
                        mediaPlayer.pauseMediaPlayer()
                    }

                    is HomeSideEffect.StopMusic -> {
                        Timber.tag("StreamMusic").d("Dispose Called2")
                        mediaPlayer.endMediaPlayer()
                    }
                }
            }
    }

    HomeScreen(
        uiState = uiState,
        onSelectMusic = viewModel::onMusicSelected,
        onPressPlay = viewModel::playPlayer,
        onPressPause = viewModel::pausePlayer,
        onChangeSortBy = viewModel::updateSortByIndex,
        onChangeSortSheetVisibility = viewModel::updateSheetVisibility,
        modifier = modifier.background(White)
    )

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.endMediaPlayer()
        }
    }
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onSelectMusic: (MusicAudioFile) -> Unit,
    onPressPlay: () -> Unit,
    onPressPause: () -> Unit,
    onChangeSortBy: (Int) -> Unit,
    onChangeSortSheetVisibility: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.isSortSheetVisible) {
        SortingBottomSheet(
            onDismiss = { index ->
                onChangeSortSheetVisibility(false)
                onChangeSortBy(index)
            },
            onSelectSortBy = onChangeSortBy,
            initialSortBy = uiState.sortByIndex
        )
    }

    Column(
        modifier = modifier
    ) {
        LogoTopAppBar()

        BasicSpacer(height = 20.dp)

        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = DesignSystemR.drawable.ic_music),
                contentDescription = "",
                tint = MyVersionSub1
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Musics for My Version",
                style = MaterialTheme.typography.titleSmall,
                color = Black
            )
            Spacer(modifier = Modifier.weight(1f))

            SortingButton(
                text = stringResource(SortBy.entries[uiState.sortByIndex].sortBy),
                onClick = { onChangeSortSheetVisibility(true) }
            )
        }

        BasicSpacer(height = 12.dp)

        HorizontalDivider(
            thickness = 1.dp,
            color = Grey200,
            modifier = Modifier.padding(
                horizontal = 20.dp
            )
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (uiState.loadState) {
                UiState.Loading -> {}
                UiState.Empty -> {}
                is UiState.Failure -> {}
                is UiState.Success -> {
                    SuccessScreen(
                        musicList = uiState.loadState.data,
                        onMusicSelected = onSelectMusic
                    )
                }
            }
        }

        AudioPlayBox(
            title = uiState.currentMusic?.title,
            subTitle = uiState.currentMusic?.artist,
            isPlaying = uiState.isMusicPlaying,
            onClickPlayButton = onPressPlay,
            onClickPauseButton = onPressPause
        )
    }
}

@Composable
private fun SuccessScreen(
    musicList: List<MusicAudioFile>,
    onMusicSelected: (MusicAudioFile) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        itemsIndexed(musicList) { index, music ->
            MyVersionVerticalItem(
                itemType = VerticalItemType.COVER,
                iconColor = Black,
                onClick = { onMusicSelected(music) },
                title = music.title,
                subTitle = music.artist
            )
            if (index < musicList.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

