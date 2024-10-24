package com.my.version.core.designsystem.component.bottomsheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.my.version.core.designsystem.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicBottomSheet(
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState,
        containerColor = White,
    ) {
        content()
    }
}