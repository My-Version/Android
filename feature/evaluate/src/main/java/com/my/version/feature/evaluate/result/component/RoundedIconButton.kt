package com.my.version.feature.evaluate.result.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.component.divider.BasicSpacer
import com.my.version.core.designsystem.theme.MyVersionSub1
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.MyVersionTypography
import com.my.version.core.designsystem.theme.White
import com.my.version.core.designsystem.R as DesignSystemR

@Composable
internal fun RoundedIconButton(
    @DrawableRes
    iconRes: Int,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .noRippleClickable { onClick() }
            .wrapContentSize()
            .background(
                color = MyVersionSub1,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "",
                tint = White
            )
            BasicSpacer(width = 10.dp)
            Text(
                text = text,
                color = White,
                style = MyVersionTypography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoundedIconButtonPreview() {
    MyVersionTheme {
        RoundedIconButton(
            iconRes = DesignSystemR.drawable.ic_thumbs_up_16,
            text = "Similarity"
        )
    }
}