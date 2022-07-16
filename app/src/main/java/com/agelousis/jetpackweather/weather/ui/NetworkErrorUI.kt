package com.agelousis.jetpackweather.weather.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.agelousis.jetpackweather.R
import com.airbnb.lottie.compose.*

@Composable
fun NetworkErrorLayout(
    state: Boolean,
    modifier: Modifier = Modifier
) {
    if (state) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                resId = R.raw.no_internet_animation
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
fun NetworkErrorLayoutPreview() {
    NetworkErrorLayout(
        state = true
    )
}