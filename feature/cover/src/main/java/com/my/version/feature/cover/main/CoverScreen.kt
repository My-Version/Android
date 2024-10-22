package com.my.version.feature.cover.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import com.my.version.core.designsystem.component.topappbar.NewCreationTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.CoverGradient1
import com.my.version.core.designsystem.theme.CoverGradient2
import com.my.version.core.designsystem.theme.CoverGradient3
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.type.SortBy
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.domain.entity.CoverAudioFile
import com.my.version.feature.cover.R
import com.my.version.feature.cover.main.state.CoverUiState
import java.io.File

@Composable
fun CoverRoute(
    navigateToSelect: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CoverViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val mediaPlayer = remember { StreamMediaPlayer(context) }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    CoverSideEffect.PauseCoverAudio -> {
                        mediaPlayer.pauseMediaPlayer()
                    }

                    CoverSideEffect.PlayCoverAudio -> {
                        mediaPlayer.playMediaPlayer()
                    }

                    is CoverSideEffect.StartCoverAudio -> {
                        with(mediaPlayer) {
                            endMediaPlayer()
                            prepareMediaPlayer(sideEffect.uri)
                        }
                    }
                }
            }
    }

    CoverScreen(
        modifier = modifier,
        uiState = uiState,
        onCreateClicked = navigateToSelect,
        onChangeSortBy = viewModel::updateSortByIndex,
        onChangeSortSheetVisibility = viewModel::updateSheetVisibility,
        onCoverSelected = {
            viewModel.playCoverAudio(it)
        }
    )

    DisposableEffect(true) {
        onDispose {
            mediaPlayer.endMediaPlayer()
        }
    }
}

@Composable
private fun CoverScreen(
    modifier: Modifier = Modifier,
    uiState: CoverUiState,
    onCreateClicked: () -> Unit,
    onCoverSelected: (File?) -> Unit,
    onChangeSortBy: (Int) -> Unit = {},
    onChangeSortSheetVisibility: (Boolean) -> Unit,
) {
    val commonModifier = Modifier.padding(horizontal = 20.dp)
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
        NewCreationTopAppBar(
            title = stringResource(id = R.string.cover_topbar_main),
            textStyle = MaterialTheme.typography.labelLarge,
            onClick = onCreateClicked,
        )

        BasicSpacer(height = 20.dp)

        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = com.my.version.core.designsystem.R.drawable.ic_music),
                contentDescription = "",
                tint = CoverGradient1
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(id = R.string.cover_main_title),
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
                UiState.Empty -> {
                    EmptyScreen(
                        modifier = commonModifier
                    )
                }

                is UiState.Failure -> {}
                is UiState.Success -> {
                    SuccessScreen(
                        coverList = uiState.loadState.data,
                        onCoverSelected = onCoverSelected,
                        modifier = commonModifier
                    )
                }

            }
        }

        AudioPlayBox(
            title = uiState.currentAudio?.title,
            subTitle = uiState.currentAudio?.createdDate,
            colorList = listOf(
                CoverGradient1, CoverGradient2, CoverGradient3
            )
        )
    }
}

@Composable
private fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = stringResource(id = R.string.cover_empty_screen),
            textAlign = TextAlign.Center,
            color = Grey350,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 70.dp)
        )
    }
}

@Composable
private fun SuccessScreen(
    coverList: List<CoverAudioFile>,
    onCoverSelected: (File?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        itemsIndexed(coverList) { index, cover ->
            MyVersionVerticalItem(
                itemType = VerticalItemType.COVER,
                iconColor = Black,
                onClick = { onCoverSelected(cover.audio) },
                title = cover.title,
                subTitle = stringResource(id = R.string.cover_created_date, cover.createdDate)
            )
            if (index < coverList.size - 1) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
