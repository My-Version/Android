package com.my.version.core.designsystem.component.slider


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.MyVersionSub5
import com.terning.core.util.NoRippleInteractionSource

@Composable
fun AudioPlayBoxSlider(
    progress: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 3.dp
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Slider(
            value = progress,
            valueRange = 0f..1f,
            onValueChange = onValueChange,
            interactionSource = NoRippleInteractionSource,
            colors = SliderDefaults.colors(
                activeTrackColor = MyVersionSub5,
                inactiveTrackColor = Grey350,
                thumbColor = MyVersionSub5
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Preview(showBackground = false)
@Composable
private fun AudioPlayBoxSliderPreview() {
    AudioPlayBoxSlider(
        progress = 0.6f,
        onValueChange = {}
    )
}