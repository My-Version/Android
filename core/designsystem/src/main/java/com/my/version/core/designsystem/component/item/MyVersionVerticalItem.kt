package com.my.version.core.designsystem.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.theme.MyVersionMain
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White
import com.my.version.core.designsystem.type.VerticalItemType

@Composable
fun MyVersionVerticalItem(
    itemType: VerticalItemType,
    onClick: () -> Unit,
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
    iconColor: Color = Black
) {
    MyVersionBasicItem(
        color = White,
        cornerRadius = 10,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(end = 16.dp)
            ) {
                SingleLineText(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(6.dp))
                SingleLineText(
                    text = subTitle,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            
            MyVersionBasicIconButton(
                icon = itemType.icon,
                contentDescription = stringResource(id = itemType.contentDescription),
                onClick = onClick,
                color = iconColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerticalMusicListItemPreview() {
    MyVersionTheme {
        MyVersionVerticalItem(
            itemType = VerticalItemType.MUSIC,
            iconColor = MyVersionMain,
            onClick = {},
            title = stringResource(id = R.string.preview_title), 
            subTitle = stringResource(id = R.string.preview_body))
    }
}
