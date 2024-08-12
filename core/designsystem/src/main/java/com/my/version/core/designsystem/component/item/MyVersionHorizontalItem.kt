package com.my.version.core.designsystem.component.item

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.R
import com.my.version.core.designsystem.component.button.MyVersionBasicIconButton
import com.my.version.core.designsystem.component.text.SingleLineText
import com.my.version.core.designsystem.theme.Grey500
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White

@Composable
fun MyVersionHorizontalItem(
    @DrawableRes icon: Int,
    contentDescription: String,
    iconColor: Color,
    onClick: () -> Unit,
    title: String,
    subTitle: String,
    body: String,
    modifier: Modifier = Modifier
) {
    val spacerModifier = Modifier.height(6.dp)

    MyVersionBasicItem(
        color = White,
        cornerRadius = 10
    ) {
        Column(
            modifier = modifier
                .size(
                    width = 120.dp,
                    height = 130.dp
                )
                .padding(16.dp)
        ) {
            SingleLineText(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = spacerModifier)

            SingleLineText(
                text = subTitle,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = spacerModifier)

            SingleLineText(
                text = body,
                style = MaterialTheme.typography.labelSmall,
                color = Grey500
            )
        }

        Box(
            modifier = Modifier.matchParentSize()
        ) {
            MyVersionBasicIconButton(
                icon = icon,
                contentDescription = contentDescription,
                onClick = onClick,
                color = iconColor,
                modifier = Modifier.align(Alignment.BottomEnd).padding(end = 11.dp, bottom = 11.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalListItemPreview() {
    MyVersionTheme {
        MyVersionHorizontalItem(
            icon = R.drawable.ic_forward,
            iconColor = MyVersionMain,
            contentDescription = "",
            onClick = {},
            title = stringResource(id = R.string.preview_title),
            subTitle = stringResource(id = R.string.preview_subtitle),
            body = stringResource(id = R.string.preview_subtitle)
        )
    }
}