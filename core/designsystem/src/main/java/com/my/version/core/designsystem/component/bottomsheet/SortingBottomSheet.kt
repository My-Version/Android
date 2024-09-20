package com.my.version.core.designsystem.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey200
import com.my.version.core.designsystem.theme.Grey400
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.core.designsystem.type.SortBy
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingBottomSheet(
    onDismiss: (Int) -> Unit,
    initialSortBy: Int,
    modifier: Modifier = Modifier,
    onSelectSortBy: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var currentSortBy by remember { mutableIntStateOf(initialSortBy) }

    BasicBottomSheet(
        content = {
            Text(
                text = "Sort",
                style = MyVersionTypography.titleMedium,
                color = Black,
                modifier = modifier
                    .padding(start = 27.dp, bottom = 16.dp)
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Grey200,
                modifier = modifier.padding(horizontal = 24.dp)
            )

            LazyColumn(
                modifier = modifier
                    .padding(top = 12.dp, bottom = 19.dp)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(SortBy.entries.size) { sortIndex ->
                    Text(
                        text = stringResource(id = SortBy.entries[sortIndex].sortBy),
                        style = MyVersionTypography.labelLarge,
                        color = if (currentSortBy == sortIndex) MyVersionMain else Grey400,
                        textAlign = TextAlign.Start,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 3.dp)
                            .padding(vertical = 12.dp)
                            .noRippleClickable {
                                currentSortBy = sortIndex
                                onSelectSortBy(sortIndex)
                                scope
                                    .launch { sheetState.hide() }
                                    .invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            onDismiss(currentSortBy)
                                        }
                                    }
                            }
                    )
                }
            }
        },
        onDismissRequest = { onDismiss(currentSortBy) },
        sheetState = sheetState
    )
}
