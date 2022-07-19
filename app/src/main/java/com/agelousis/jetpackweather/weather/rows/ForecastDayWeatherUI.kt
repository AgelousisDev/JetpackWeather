package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.CurrentDayWeatherDataModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.textViewHeaderFont
import com.airbnb.lottie.compose.*

@Composable
fun ForecastDayWeatherLayout(
    modifier: Modifier = Modifier,
    currentDayWeatherDataModel: CurrentDayWeatherDataModel
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                other = modifier
            )
    ) {
        val (temperatureLabelConstrainedReference, iconConstrainedReference,
            conditionConstrainedReference, windLayoutConstrainedReference
        ) = createRefs()
        // C
        Text(
            text = currentDayWeatherDataModel currentMinMaxTemperatureUnitFormatted context,
            style = textViewHeaderFont,
            modifier = Modifier
                .constrainAs(temperatureLabelConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        // Condition Icon
        Image(
            painter = rememberAsyncImagePainter(
                model = currentDayWeatherDataModel.weatherConditionDataModel?.iconUrl
            ),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(iconConstrainedReference) {
                    top.linkTo(temperatureLabelConstrainedReference.top)
                    start.linkTo(temperatureLabelConstrainedReference.end, 16.dp)
                    width = Dimension.value(
                        dp = 40.dp
                    )
                    height = Dimension.value(
                        dp = 40.dp
                    )
                }
        )

        // Condition Text
        Text(
            text = currentDayWeatherDataModel.weatherConditionDataModel?.text ?: "",
            style = Typography.bodyMedium,
            color = colorResource(id = R.color.grey),
            modifier = Modifier
                .constrainAs(conditionConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(iconConstrainedReference.bottom, 0.dp)
                    end.linkTo(parent.end)
                }
        )

        // Wind Layout
        Row(
            modifier = Modifier
                .constrainAs(windLayoutConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(conditionConstrainedReference.bottom, 16.dp)
                    end.linkTo(parent.end)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp
            )
        ) {
            Text(
                text = currentDayWeatherDataModel.maxWindKph?.toInt()?.toString()
                    ?: "",
                style = textViewHeaderFont,
                color = colorResource(
                    id = currentDayWeatherDataModel.windStateColor
                )
            )
            Text(
                text = currentDayWeatherDataModel.getWindStateWarning(
                    context = context
                ),
                style = Typography.displayMedium,
                color = colorResource(
                    id = currentDayWeatherDataModel.windStateColor
                )
            )
            val windLottieComposition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    resId = R.raw.wind_animation
                )
            )
            val windLottieProgress by animateLottieCompositionAsState(
                windLottieComposition,
                iterations = LottieConstants.IterateForever,
                restartOnPlay = false
            )
            LottieAnimation(
                composition = windLottieComposition,
                progress = {
                    windLottieProgress
                },
                modifier = Modifier
                    .size(
                        size = 50.dp
                    )
            )
        }
    }
}