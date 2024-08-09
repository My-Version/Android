package com.my.version.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.my.version.core.designsystem.R

val PretendardLight = FontFamily(Font(R.font.pretendard_light))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold))

val MyVersionTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = PretendardSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.005).em,
    ),
    headlineMedium = TextStyle(
        fontFamily = PretendardSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 23.sp,
        lineHeight = 23.sp,
        letterSpacing = (-0.005).em,
    ),
    headlineSmall = TextStyle(
        fontFamily = PretendardSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 21.sp,
        lineHeight = 27.sp,
        letterSpacing = (-0.005).em,
    ),
    titleLarge = TextStyle(
        fontFamily = PretendardSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        lineHeight = 23.sp,
        letterSpacing = (-0.005).em,
    ),
    titleMedium = TextStyle(
        fontFamily = PretendardSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.005).em,
    ),
    titleSmall = TextStyle(
        fontFamily = PretendardMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 19.sp,
        letterSpacing = (-0.005).em,
    ),
    bodyLarge = TextStyle(
        fontFamily = PretendardMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.005).em,
    ),
    bodyMedium = TextStyle(
        fontFamily = PretendardRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        letterSpacing = (-0.005).em,
    ),
    bodySmall = TextStyle(
        fontFamily = PretendardRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = (-0.005).em,
    ),
    labelLarge = TextStyle(
        fontFamily = PretendardRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        letterSpacing = (-0.005).em,
    ),
    labelMedium = TextStyle(
        fontFamily = PretendardLight,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = (-0.005).em,
    ),
    labelSmall = TextStyle(
        fontFamily = PretendardLight,
        fontWeight = FontWeight.Light,
        fontSize = 11.sp,
        lineHeight = 11.sp,
        letterSpacing = (-0.005).em,
    )
)
