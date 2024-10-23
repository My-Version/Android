package com.my.version.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.Grey500
import com.my.version.core.designsystem.theme.MyVersionSub5

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    cursorColor: Color = Black,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = Grey350,
    hintColor: Color = Grey350,
    valueColor: Color = Grey500,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    ) {
    CustomBasicTextField(
        value = value,
        hint = hint,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(cursorColor),
        hintColor = hintColor,
        valueColor = valueColor,
        visualTransformation = visualTransformation,
        modifier = modifier.fillMaxWidth()
            .height(50.dp)
            .background(
                color = backgroundColor,
            )
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun AuthTextFieldPreview() {
    AuthTextField(
        value = "TextField Test",
        onValueChange = {}
    )
}