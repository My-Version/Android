package com.my.version.core.designsystem.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.my.version.core.designsystem.R

enum class VerticalItemType(
    @DrawableRes val icon: Int,
    @StringRes val contentDescription: Int
) {
    MUSIC(
        icon = R.drawable.ic_play,
        contentDescription = R.string.btn_play
    ),
    COVER(
        icon = R.drawable.ic_play,
        contentDescription = R.string.btn_play
    ),
    AUDIO(
        icon = R.drawable.ic_close,
        contentDescription = R.string.btn_close
    ),
    EVALUATE(
        icon = R.drawable.ic_forward,
        contentDescription = R.string.btn_next
    ),
}