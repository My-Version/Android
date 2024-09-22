package com.my.version.feature.evaluate.select.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudioFile
import java.io.File

data class EvaluationSelectUiState(
    val loadState: UiState<List<CoverAudioFile>> = UiState.Loading,
    val selected: Int = -1,
    val sortByIndex: Int = 0,
    val isSortSheetVisible: Boolean = false
)

val tempCoverList = listOf<CoverAudioFile>(
    CoverAudioFile(
        title = "Fool",
        createdDate = "2024/09/20",
        audio = File("/storage/emulated/0/Android/data/com.my.version/files/Music/Fool_BigBang.mp3")
    ),
    CoverAudioFile(
        title = "Wonderful",
        createdDate = "2024/08/30",
        audio = File("/storage/emulated/0/Android/data/com.my.version/files/Music/Wonderful_BigBang.mp3")
    ),
    CoverAudioFile(
        title = "Tonight",
        createdDate = "2024/09/10",
        audio = File("/storage/emulated/0/Android/data/com.my.version/files/Music/Tonight_BigBang.mp3")
    ),
    CoverAudioFile(
        title = "Ditto",
        createdDate = "2024/09/02",
        audio = File("/storage/emulated/0/Android/data/com.my.version/files/Music/Ditto_NewJeans.mp3")
    )
)