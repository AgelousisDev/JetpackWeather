package com.agelousis.jetpackweather.ui.rows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CircularDotLayout(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceTint
) {
    Box(
        modifier = modifier
            .background(
                color = color,
                shape = CircleShape
            )
    )
}

@Preview
@Composable
fun CircularDotLayoutPreview() {
    CircularDotLayout()
}