package com.my.version.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my.version.core.designsystem.theme.MyVersionBackground
import com.my.version.core.designsystem.theme.MyVersionTheme
import com.my.version.core.designsystem.theme.White
import com.my.version.core.designsystem.type.SortingButtonType
import com.terning.core.util.NoRippleTheme

@Composable
fun SortingButton(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sortType = if (isSelected) {
        SortingButtonType.FOLD
    } else {
        SortingButtonType.UNFOLD
    }

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = White,
                containerColor = Color.Transparent
            ),
            modifier = modifier
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                painter = painterResource(id = sortType.icon),
                contentDescription = stringResource(id = sortType.contentDescription)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SortingButtonPreview() {
    MyVersionTheme {
        Box(
            modifier = Modifier.background(MyVersionBackground)
        ) {
            SortingButton(
                isSelected = false,
                text = "최신순",
                onClick = {}
            )
        }
    }
}