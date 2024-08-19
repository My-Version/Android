package com.my.version.feature.cover.upload

import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.divider.TitleWithDivider
import com.my.version.core.designsystem.component.item.MyVersionVerticalItem
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.feature.cover.R
import com.my.version.feature.cover.component.OutlinedTextButton
import com.my.version.feature.cover.upload.component.uploadResultLauncher
import timber.log.Timber
import java.io.File

@Composable
fun CoverUploadRoute(
    modifier: Modifier = Modifier,
    viewModel: CoverUploadViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = lifecycleOwner
    )
    val recordResultLauncher = uploadResultLauncher(
        onResultOk = {dataUri ->
            viewModel.addAudioFile(File(dataUri.toString()))
        }
    )
    val fileResultLauncher = uploadResultLauncher(
        onResultOk = {dataUri ->
            viewModel.addAudioFile(File(dataUri.toString()))
            Timber.tag("Upload").d(dataUri.toString())
        }
    )



    CoverUploadScreen(
        modifier = modifier,
        fileList = uiState.uploadedFiles,
        onRecordButtonClicked = {
            val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
            recordResultLauncher.launch(intent)
        },
        onFileSystemButtonClicked = {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "audio/*"
            }
            fileResultLauncher.launch(intent)
        },
        onCancelButtonClicked = { index ->
            viewModel.removeAudioFile(index)
        }
    )
}

@Composable
internal fun CoverUploadScreen(
    onRecordButtonClicked: () -> Unit,
    onFileSystemButtonClicked: () -> Unit,
    onCancelButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    fileList: List<File> = emptyList()
) {
    val paddingModifier = modifier.padding(horizontal = 20.dp)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = {},
            title = stringResource(id = R.string.cover_topbar_upload)
        )

        TitleWithDivider(
            text = stringResource(id = R.string.cover_on_boarding_title2),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = paddingModifier
        )

        Row(
            modifier = paddingModifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp)
        ) {
            OutlinedTextButton(
                onClick = onRecordButtonClicked,
                text = stringResource(id = R.string.cover_button_audio_record)
            )
            BasicSpacer(width = 10.dp)
            OutlinedTextButton(
                onClick = onFileSystemButtonClicked,
                text = stringResource(id = R.string.cover_button_file_system)
            )
        }
        BasicSpacer(height = 6.dp)

        LazyColumn(
            modifier = paddingModifier
                .weight(1f),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            itemsIndexed(fileList) { index, file ->
                MyVersionVerticalItem(
                    itemType = VerticalItemType.AUDIO,
                    iconColor = Black,
                    onClick = {
                        onCancelButtonClicked(index)
                    },
                    title = "",
                    subTitle = ""
                )
                if (fileList.last() != file) {
                    BasicSpacer(height = 16.dp)
                }
            }
        }

        Box(
            modifier = Modifier.wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            RectangleButton(
                isEnabled = false,
                text = "Next",
                textStyle = MaterialTheme.typography.titleMedium,
                innerPadding = 20,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoverUploadScreenPreview() {
    MyVersionTheme {
        CoverUploadScreen(
            fileList = emptyList(),
            modifier = Modifier.background(MyVersionBackground),
            onRecordButtonClicked = {},
            onCancelButtonClicked = {},
            onFileSystemButtonClicked = {}
        )
    }
}