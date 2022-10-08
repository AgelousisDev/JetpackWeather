package com.agelousis.jetpackweather.ui.theme

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration

@OptIn(ExperimentalAnimationApi::class)
val slideVertically = slideInVertically(
    animationSpec = tween(500),
    initialOffsetY = { fullWidth -> fullWidth }
) with
        slideOutVertically(
            animationSpec = tween(500),
            targetOffsetY = { fullWidth -> -fullWidth }
        )

@Composable
fun weatherBackgroundGradient(): Brush {
    return when(LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE ->
            Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.colorScheme.surface,
                )
            )
        else ->
            Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.colorScheme.surfaceVariant
                )
            )
    }
}