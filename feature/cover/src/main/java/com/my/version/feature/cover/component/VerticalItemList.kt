package com.my.version.feature.cover.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.component.item.VerticalListItem
import com.my.version.core.designsystem.theme.Black
import com.my.version.core.designsystem.type.TempItem
import com.my.version.core.designsystem.type.VerticalItemType


internal fun LazyListScope.verticalItemList(
    coverList: List<TempItem>,
    itemType: VerticalItemType
) {
    items(coverList) { cover ->
        VerticalListItem(
            itemType = itemType,
            iconColor = Black,
            onClick = { /*TODO*/ },
            title = cover.title,
            subTitle = cover.body
        )

        if (coverList.last() != cover) {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}