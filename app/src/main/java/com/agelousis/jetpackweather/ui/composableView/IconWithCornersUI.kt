package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.ui.theme.Butterscotch

@Composable
fun IconWithCornersLayout(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    iconResourceId: Int,
    cornerSizeRadius: Dp = 16.dp
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(
                    size = cornerSizeRadius
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResourceId),
            contentDescription = null,
            modifier = Modifier
                .size(
                    width = 42.dp,
                    height = 42.dp
                )
        )
    }
}

@Preview
@Composable
fun IconWithCornersLayoutPreview() {
    IconWithCornersLayout(
        backgroundColor = colorResource(id = R.color.yellowLighter),
        iconResourceId = R.drawable.ic_warning_alert
    )
}