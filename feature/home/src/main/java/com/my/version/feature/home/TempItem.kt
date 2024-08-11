package com.my.version.feature.home

data class TempItem(
    val title: String,
    val subTitle: String,
    val body: String
)

val tempList1 = listOf(
    TempItem(
        "title1", "subTitle1", "body1"
    ),
    TempItem(
        "title2", "subTitle2", "body2"
    ),
    TempItem(
        "title3", "subTitle3", "body3"
    ),
    TempItem(
        "title4", "subTitle4", "body4"
    ),
    TempItem(
        "title5", "subTitle5", "body5"
    ),
)