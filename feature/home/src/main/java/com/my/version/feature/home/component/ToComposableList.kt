package com.my.version.feature.home.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.my.version.core.designsystem.component.item.HorizontalListItem
import com.my.version.core.designsystem.theme.Black
import com.my.version.feature.home.R as HomeR
import com.my.version.core.designsystem.R as DesignSystemR
import com.my.version.feature.home.TempItem

@Composable
fun musicToComposableList(
    musicList: List<TempItem>
): List<@Composable () -> Unit> {
    val commonItem: List<CommonItem> = musicList.map { item ->
        CommonItem(
            title = item.title,
            subTitle = item.subTitle,
            body = item.body,
            onClick = {}
        )
    }
    return toComposableList(
        icon = DesignSystemR.drawable.ic_play,
        itemList = commonItem
    )
}

@Composable
fun coverToComposableList(
    coverList: List<TempItem>
) : List<@Composable () -> Unit> {
    val commonItem: List<CommonItem> = coverList.map { item ->
        CommonItem(
            title = item.title,
            subTitle = item.subTitle,
            body = item.body,
            onClick = {}
        )
    }
    return toComposableList(
        icon = DesignSystemR.drawable.ic_play,
        itemList = commonItem
    )
}

@Composable
fun evaluationToComposableList(
    evaluationList: List<TempItem>
) : List<@Composable () -> Unit> {
    val commonItem: List<CommonItem> = evaluationList.map { item ->
        CommonItem(
            title = item.title,
            subTitle = item.subTitle,
            body = item.body,
            onClick = {}
        )
    }
    return toComposableList(
        icon = DesignSystemR.drawable.ic_forward,
        itemList = commonItem
    )
}


@Composable
private fun toComposableList(
    @DrawableRes icon: Int,
    itemList: List<CommonItem>
): List<@Composable () -> Unit> {
    val composableList = mutableListOf<@Composable () -> Unit>()
    itemList.forEach { item ->
        composableList.add {
            HorizontalListItem(
                icon = icon,
                contentDescription = stringResource(id = DesignSystemR.string.icon_content_description_play),
                iconColor = Black,
                onClick = item.onClick,
                title = item.title,
                subTitle = item.subTitle,
                body = item.body
            )
        }
    }
    return composableList
}

private data class CommonItem(
    val title: String,
    val subTitle: String,
    val body: String,
    val onClick: () -> Unit
)
