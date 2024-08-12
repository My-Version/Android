package com.my.version.core.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.my.version.core.common.extension.noRippleClickable
import com.my.version.core.designsystem.theme.MyVersionBackground

@Composable
fun MyVersionBasicIconButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    backgroundColor: Color = Color.Transparent,
) {
    MyVersionBasicButton(
        buttonColors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = color
        ),
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = color
        )
    }
}