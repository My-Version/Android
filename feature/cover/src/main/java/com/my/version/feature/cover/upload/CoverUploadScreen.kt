package com.my.version.feature.cover.upload

import android.content.Intent
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
import com.my.version.core.designsystem.component.item.MyVersionVerticalItemTwoButton
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.domain.entity.RecordAudioFile
import com.my.version.feature.cover.R
import com.my.version.feature.cover.component.OutlinedTextButton
import com.my.version.feature.cover.record.RecordDialog
import com.my.version.feature.cover.upload.component.uploadResultLauncher
import timber.log.Timber
import java.io.File

@Composable
fun CoverUploadRoute(
    onNavigateUp: () -> Unit,
    onUploadComplete: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CoverUploadViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = lifecycleOwner
    )

    val fileResultLauncher = uploadResultLauncher(
        onResultOk = {dataUri ->
            val file = File(dataUri.toString())
            viewModel.addRecordFile(file.absolutePath)
            Timber.tag("Upload").d(file.absolutePath)
        }
    )

    if(uiState.recordDialogVisibility) {
        RecordDialog(
            onDismissRequest = { filePath ->
                viewModel.updateRecordDialogVisibility(false)
                if(filePath.isNotBlank())
                   viewModel.addRecordFile(filePath)
            }
        )
    }

    CoverUploadScreen(
        modifier = modifier,
        fileList = uiState.uploadFiles,
        onNavigateUp = onNavigateUp,
        onRecordButtonClicked = { viewModel.updateRecordDialogVisibility(true) },
        onFileSystemButtonClicked = {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "audio/*"
            }
            fileResultLauncher.launch(intent)
        },
        onCancelButtonClicked = viewModel::removeRecordFile,
        onPlayButtonClicked = viewModel::playRecordFile,
        onUploadComplete = {
            viewModel.clearRecordFiles()
            onUploadComplete()
        }
    )
}

@Composable
fun CoverUploadScreen(
    onNavigateUp: () -> Unit,
    onUploadComplete: () -> Unit,
    onRecordButtonClicked: () -> Unit,
    onFileSystemButtonClicked: () -> Unit,
    onCancelButtonClicked: (Int) -> Unit,
    onPlayButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    fileList: List<RecordAudioFile> = emptyList()
) {
    val paddingModifier = modifier.padding(horizontal = 20.dp)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = onNavigateUp,
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
                MyVersionVerticalItemTwoButton(
                    firstItemType = VerticalItemType.MUSIC,
                    secondItemType = VerticalItemType.AUDIO,
                    onClickFirstItem = { onPlayButtonClicked(index) },
                    onClickSecondItem  = { onCancelButtonClicked(index) },
                    title = file.title,
                    subTitle = file.time
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
                isEnabled = fileList.isNotEmpty(),
                text = "Next",
                textStyle = MaterialTheme.typography.titleMedium,
                innerPadding = 20,
                modifier = Modifier.fillMaxWidth(),
                onClick = onUploadComplete
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoverUploadScreenPreview() {
        Box {
            CoverUploadScreen(
                fileList = emptyList(),
                modifier = Modifier.background(color = MyVersionBackground),
                onRecordButtonClicked = {},
                onCancelButtonClicked = {},
                onFileSystemButtonClicked = {},
                onNavigateUp = {},
                onUploadComplete = {},
                onPlayButtonClicked = {}
            )
        }

}