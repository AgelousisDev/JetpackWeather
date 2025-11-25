package com.agelousis.jetpackweather.ui.theme

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration

val slideVertically = slideInVertically(
    animationSpec = tween(500),
    initialOffsetY = { fullWidth -> fullWidth }
) togetherWith slideOutVertically(
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