package com.my.version.core.designsystem.type

import androidx.annotation.StringRes
import com.my.version.core.designsystem.R

enum class SortBy(
    @StringRes val sortBy: Int,
    val type: String,
) {
    EARLIEST(R.string.sort_by_earliest, "earliest"),
    LATEST(R.string.sort_by_latest, "latest"),
    ALPHABETIC_ASC(R.string.sort_by_ascending, "asc"),
    ALPHABETIC_DESC(R.string.sort_by_descending, "desc"),
}