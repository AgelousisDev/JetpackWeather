package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.R

@Composable
fun VerticalProgress(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    val mProgress = animateFloatAsState(
        targetValue = progress / 100
    )
    Column(
        modifier = Modifier
            .width(
                width = 20.dp
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 12.dp
                )
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    elevation = 20.dp
                )
            )
            .then(
                other = modifier
            )
    ) {
        Box(
            modifier = Modifier
                .weight(
                    weight = if ((1 - mProgress.value) == 0f) 0.0001f else 1 - mProgress.value
                )
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = 12.dp
                    )
                )
                .weight(
                    weight = mProgress.value
                )
                .fillMaxWidth()
                .background(
                    color = color
                )
        )
    }
}

@Preview
@Composable
fun VerticalProgressPreview() {
    VerticalProgress(
        progress = 50f,
        color = colorResource(id = R.color.orange)
    )
}