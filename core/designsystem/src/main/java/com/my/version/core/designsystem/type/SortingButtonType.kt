package com.my.version.core.designsystem.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.my.version.core.designsystem.R

enum class SortingButtonType(
    @DrawableRes val icon: Int,
    @StringRes val contentDescription: Int
) {
    FOLD(
        icon = R.drawable.ic_up,
        contentDescription = R.string.btn_sorting_fold
    ),
    UNFOLD(
        icon = R.drawable.ic_down,
        contentDescription = R.string.btn_sorting_unfold
    );
}