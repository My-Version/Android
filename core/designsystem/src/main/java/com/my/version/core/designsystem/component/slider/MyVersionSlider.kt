package com.my.version.core.designsystem.component.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.MyVersionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyVersionSlider(
    progress: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        Slider(
            value = progress,
            valueRange = 0f..1f,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            thumb = {
                Box(modifier = Modifier.matchParentSize(),
                    contentAlignment = Alignment.Center) {
                    SliderDefaults.Thumb(
                        interactionSource = interactionSource,
                        thumbSize = DpSize(10.dp, 10.dp)

                    )
                }
            }

        )
    }
}


@Preview(showBackground = true)
@Composable
private fun MyVersionSliderPreview() {
    MyVersionTheme {
        MyVersionSlider(
            progress = 0.6f,
            onValueChange = {}
        )
    }
}