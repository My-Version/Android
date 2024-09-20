package com.my.version.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.Grey300
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.type.SortBy

@Composable
fun SortingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    sortBy: Int = 0,
    textColor: Color = Black,
    iconColor: Color = Grey300,
    isSelected: Boolean = true,
) {
    Row(
        modifier = modifier
            .noRippleClickable(onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            modifier = Modifier
                .padding(
                    top = 6.dp,
                    bottom = 6.dp,
                )
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_down),
            contentDescription = "",
            tint = iconColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SortingButtonPreview() {
    MyVersionTheme {
        Box(
            modifier = Modifier.background(MyVersionBackground)
        ) {
            SortingButton(
                isSelected = false,
                text = "최신순",
                onClick = {}
            )
        }
    }
}