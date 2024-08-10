package com.my.version.core.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.my.version.core.common.extension.noRippleClickable

@Composable
fun MyVersionBasicIconButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
    contentDescription: String = ""
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = contentDescription,
        modifier = modifier.noRippleClickable { onClick() },
        tint = color
    )
}