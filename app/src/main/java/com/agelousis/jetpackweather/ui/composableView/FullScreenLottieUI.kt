package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.agelousis.jetpackweather.R
import com.airbnb.lottie.compose.*

@Composable
fun FullScreenLottieLayout(
    state: Boolean,
    lottieAnimationResourceId: Int,
    modifier: Modifier = Modifier
) {
    if (state) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                resId = lottieAnimationResourceId
            )
        )
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            restartOnPlay = false
        )
        LottieAnimation(
            composition = composition,
            progress = {
                progress
            },
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun FullScreenLottieLayoutPreview() {
    FullScreenLottieLayout(
        state = true,
        lottieAnimationResourceId = R.raw.no_internet_animation
    )
}