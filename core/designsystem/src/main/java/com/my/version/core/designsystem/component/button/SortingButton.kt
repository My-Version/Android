package com.my.version.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White
import com.my.version.core.designsystem.type.SortingButtonType

@Composable
fun SortingButton(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sortType = if (isSelected) {
        SortingButtonType.FOLD
    } else {
        SortingButtonType.UNFOLD
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.noRippleClickable {
            onClick()
        }
    ) {
        Text(
            color = White,
            style = MaterialTheme.typography.labelMedium,
            text = text,
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(6.dp))
        Icon(
            painter = painterResource(id = sortType.icon),
            contentDescription = stringResource(id = sortType.contentDescription),
            tint = White
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