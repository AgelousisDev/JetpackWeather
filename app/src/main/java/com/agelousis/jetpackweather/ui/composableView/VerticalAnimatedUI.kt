package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable

@Composable
fun VerticalAnimatedView(
    state: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = state,
        enter = expandVertically(
            animationSpec = tween(
                durationMillis = 300, easing = FastOutLinearInEasing
            )
        ),
        exit = shrinkVertically(
            animationSpec = tween(
                durationMillis = 300, easing = FastOutLinearInEasing
            )
        ),
        content = content
    )
}