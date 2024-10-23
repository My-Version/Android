package com.my.version.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.Grey500
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionSub5

@Composable
fun CustomBasicTextField(
    value: String,
    hint: String,
    cursorBrush: Brush,
    modifier: Modifier = Modifier,
    hintColor: Color = Grey350,
    valueColor: Color = Grey500,
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    actionButton: @Composable () -> Unit = {}
) {
    BasicTextField(
        maxLines = 1,
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle.Default.copy(color = valueColor),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = hintColor
                        )
                    }
                    innerTextField()
                }
                actionButton()
            }
        },
        cursorBrush = cursorBrush,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        modifier = modifier

    )
}

@Preview(showBackground = true)
@Composable
private fun CustomBasicTextFieldPreview() {
    CustomBasicTextField(
        value = "hello",
        hint = "hint",
        cursorBrush = SolidColor(MyVersionSub5),
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
            .height(50.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 16.dp)
    )
}