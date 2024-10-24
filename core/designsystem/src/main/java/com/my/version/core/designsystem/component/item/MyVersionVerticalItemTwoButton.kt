package com.my.version.core.designsystem.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun MyVersionVerticalItemTwoButton(
    firstItemType: VerticalItemType,
    secondItemType: VerticalItemType,
    onClickFirstItem: () -> Unit,
    onClickSecondItem: () -> Unit,
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
    firstItemIconColor: Color = Black,
    secondItemIconColor: Color = Black
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
                modifier = Modifier.padding(end = 16.dp).weight(7f)
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

            Row(
                modifier = Modifier.weight(3f),
                horizontalArrangement = Arrangement.End
            ) {
                MyVersionBasicIconButton(
                    icon = firstItemType.icon,
                    contentDescription = stringResource(id = firstItemType.contentDescription),
                    onClick = onClickFirstItem,
                    color = firstItemIconColor
                )
                Spacer(modifier = Modifier.width(20.dp))
                MyVersionBasicIconButton(
                    icon = secondItemType.icon,
                    contentDescription = stringResource(id = secondItemType.contentDescription),
                    onClick = onClickSecondItem,
                    color = secondItemIconColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyVersionVerticalItemTwoButtonPreview() {
    MyVersionTheme {
        MyVersionVerticalItemTwoButton(
            firstItemType = VerticalItemType.MUSIC,
            secondItemType = VerticalItemType.AUDIO,
            onClickFirstItem = {},
            onClickSecondItem = {},
            firstItemIconColor = MyVersionMain,
            secondItemIconColor = MyVersionMain,
            title = stringResource(id = R.string.preview_title),
            subTitle = stringResource(id = R.string.preview_body)
        )
    }
}
