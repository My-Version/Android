package com.my.version.feature.auth.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.textfield.AuthTextField
import com.my.version.core.designsystem.theme.Grey350
import com.my.version.core.designsystem.theme.Grey500
import com.my.version.core.designsystem.theme.MyVersionTypography

@Composable
fun TitledAuthTextField(
    title: String,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MyVersionTypography.bodyMedium,
            color = Grey500,
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        AuthTextField(
            value = value,
            hint = hint,
            onValueChange = onValueChange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TitledAuthTextFieldPreview() {
    TitledAuthTextField(
        title = "Email",
        value = "",
        hint = "Hint",
        onValueChange = {}
    )
}