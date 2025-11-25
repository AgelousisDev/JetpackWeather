package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.agelousis.jetpackweather.BuildConfig
import com.agelousis.jetpackweather.utils.extensions.openWebViewIntent

@Composable
fun TrademarkLayout(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val trademarkIconConstrainedReference = createRef()
        AsyncImage(
            model = BuildConfig.WEATHER_API_LOGO_URL,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(trademarkIconConstrainedReference) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top)
                }
                .clickable {
                    context openWebViewIntent BuildConfig.WEATHER_API_WEB_URL
                }
        )
    }
}