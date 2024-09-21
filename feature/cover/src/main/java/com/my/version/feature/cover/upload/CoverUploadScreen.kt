package com.my.version.feature.cover.upload

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.my.version.core.designsystem.component.button.RectangleButton
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.component.item.MyVersionVerticalItemTwoButton
import com.my.version.core.designsystem.component.text.SingleLineText
import com.my.version.core.designsystem.component.topappbar.NavigateUpTopAppBar
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.Grey300
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionSub5
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.core.designsystem.type.VerticalItemType
import com.my.version.core.domain.entity.RecordAudioFile
import com.my.version.feature.cover.R
import com.my.version.feature.cover.record.RecordDialog
import com.my.version.feature.cover.upload.component.CoverUploadIconButton
import com.my.version.feature.cover.upload.component.uploadResultLauncher
import timber.log.Timber
import java.io.File
import com.my.version.core.designsystem.R as DesignSystemR

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
        onResultOk = { dataUri ->
            val file = File(dataUri.toString())
            viewModel.addRecordFile(file.absolutePath)
            Timber.tag("Upload").d(file.absolutePath)
        }
    )

    if (uiState.recordDialogVisibility) {
        RecordDialog(
            onDismissRequest = { filePath ->
                viewModel.updateRecordDialogVisibility(false)
                if (filePath.isNotBlank())
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
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NavigateUpTopAppBar(
            onNavigateUp = onNavigateUp,
            title = stringResource(id = R.string.cover_topbar_upload)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SingleLineText(
                text = stringResource(id = R.string.cover_on_boarding_title2),
                style = MaterialTheme.typography.titleSmall,
                color = Black,
                modifier = Modifier.padding(start = 10.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            CoverUploadIconButton(
                color = MyVersionSub5,
                contentDescription = stringResource(id = R.string.cover_button_audio_record),
                icon = painterResource(id = DesignSystemR.drawable.ic_mic),
                onClick = onRecordButtonClicked
            )

            BasicSpacer(width = 10.dp)

            CoverUploadIconButton(
                color = MyVersionSub5,
                contentDescription = stringResource(id = R.string.cover_button_file_system),
                icon = painterResource(id = DesignSystemR.drawable.ic_folder),
                onClick = onFileSystemButtonClicked
            )
        }

        BasicSpacer(height = 12.dp)

        HorizontalDivider(
            thickness = 1.dp,
            color = Grey200,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        BasicSpacer(height = 12.dp)

        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f),
        ) {
            if (fileList.isEmpty()) {
                EmptyListScreen()
            } else {
                AudioFileListScreen(
                    onCancelButtonClicked = onCancelButtonClicked,
                    onPlayButtonClicked = onPlayButtonClicked,
                    fileList = fileList
                )
            }
        }

        Box(
            modifier = Modifier.wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            RectangleButton(
                isEnabled = fileList.isNotEmpty(),
                text = stringResource(id = R.string.cover_button_upload),
                textStyle = MaterialTheme.typography.titleMedium,
                innerPadding = 20,
                modifier = Modifier.fillMaxWidth(),
                onClick = onUploadComplete
            )
        }
    }
}

@Composable
private fun AudioFileListScreen(
    modifier: Modifier = Modifier,
    fileList: List<RecordAudioFile>,
    onCancelButtonClicked: (Int) -> Unit,
    onPlayButtonClicked: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        itemsIndexed(fileList) { index, file ->
            MyVersionVerticalItemTwoButton(
                firstItemType = VerticalItemType.MUSIC,
                secondItemType = VerticalItemType.AUDIO,
                onClickFirstItem = { onPlayButtonClicked(index) },
                onClickSecondItem = { onCancelButtonClicked(index) },
                title = file.title,
                subTitle = file.time
            )
            if (fileList.last() != file) {
                BasicSpacer(height = 16.dp)
            }
        }
    }
}

@Composable
private fun EmptyListScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        BasicSpacer(height = 100.dp)
        Text(
            text = stringResource(id = R.string.cover_empty_guide1),
            style = MyVersionTypography.bodyLarge,
            color = Grey350
        )

        BasicSpacer(height = 40.dp)

        Text(
            text = stringResource(id = R.string.cover_empty_guide2),
            style = MyVersionTypography.bodyMedium,
            color = Grey350
        )
        BasicSpacer(height = 20.dp)

        Text(
            text = stringResource(id = R.string.cover_empty_guide3),
            style = MyVersionTypography.bodyMedium,
            color = Grey350
        )

    }
}
