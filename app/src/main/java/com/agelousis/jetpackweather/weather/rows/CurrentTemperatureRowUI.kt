package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.ui.composableView.CircularProgressbar
import com.agelousis.jetpackweather.ui.composableView.VerticalProgress
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.medium
import com.agelousis.jetpackweather.ui.theme.textViewHeaderFont
import com.airbnb.lottie.compose.*

@Composable
fun CurrentTemperatureRowLayout(
    modifier: Modifier = Modifier,
    weatherResponseModel: WeatherResponseModel
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
            conditionConstrainedReference, feelsLikeLabelConstrainedReference,
            windLayoutDividerConstrainedReference, windLayoutConstrainedReference,
            uvIndexLayoutDividerConstrainedReference, uvIndexLayoutConstrainedReference,
            humidityLayoutDividerConstrainedReference, humidityLayoutConstrainedReference) = createRefs()
        // C
        Text(
            text = weatherResponseModel.currentWeatherDataModel?.currentTemperatureUnitFormatted(
                context = context
            ) ?: "",
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
                model = weatherResponseModel.currentWeatherDataModel?.weatherConditionDataModel?.iconUrl
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
            text = weatherResponseModel.currentWeatherDataModel?.weatherConditionDataModel?.text ?: "",
            style = Typography.bodyMedium,
            color = colorResource(id = R.color.grey),
            modifier = Modifier
                .constrainAs(conditionConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(iconConstrainedReference.bottom, 8.dp)
                    end.linkTo(parent.end)
                }
        )

        // Feels Like
        Text(
            text = stringResource(
                id = R.string.key_feels_like_label,
                weatherResponseModel.currentWeatherDataModel?.feelsLikeTemperatureUnitFormatted(
                    context = context
                ) ?: ""
            ),
            style = Typography.bodyMedium,
            color = colorResource(id = R.color.grey),
            modifier = Modifier
                .constrainAs(feelsLikeLabelConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(conditionConstrainedReference.bottom, 8.dp)
                    end.linkTo(parent.end)
                }
        )

        Box(
            modifier = Modifier
                .constrainAs(windLayoutDividerConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(feelsLikeLabelConstrainedReference.bottom, 16.dp)
                    end.linkTo(parent.end)
                    width = Dimension.value(dp = 6.dp)
                    height = Dimension.value(dp = 6.dp)
                }
                .background(
                    color = MaterialTheme.colorScheme.surfaceTint,
                    shape = CircleShape
                )
        )

        // Wind Layout
        Row(
            modifier = Modifier
                .constrainAs(windLayoutConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(windLayoutDividerConstrainedReference.bottom, 16.dp)
                    end.linkTo(parent.end)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp
            )
        ) {
            Text(
                text = weatherResponseModel.currentWeatherDataModel?.windKph?.toInt()?.toString()
                    ?: "",
                style = textViewHeaderFont,
                color = colorResource(
                    id = weatherResponseModel.currentWeatherDataModel?.windStateColor
                        ?: R.color.grey
                )
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp
                )
            ) {
                if (weatherResponseModel.currentWeatherDataModel?.windDegree != null)
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_arrow_direction_down
                        ),
                        contentDescription = null,
                        tint = colorResource(id = R.color.grey),
                        modifier = Modifier
                            .size(
                                size = 15.dp
                            )
                            .rotate(
                                degrees = weatherResponseModel.currentWeatherDataModel.windDegree.toFloat()
                            )
                    )
                Text(
                    text = stringResource(id = R.string.key_km_hourly_label),
                    style = Typography.labelMedium,
                    color = colorResource(id = R.color.grey)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp
                )
            ) {
                Text(
                    text = weatherResponseModel.currentWeatherDataModel?.getWindStateWarning(
                        context = context
                    ) ?: "",
                    style = Typography.displayMedium,
                    color = colorResource(
                        id = weatherResponseModel.currentWeatherDataModel?.windStateColor
                            ?: R.color.grey
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.key_now_with_value_label,
                        weatherResponseModel.currentWeatherDataModel?.getWindDirection(
                            context = context
                        ) ?: ""
                    ),
                    style = Typography.bodyMedium
                )
            }
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

        Box(
            modifier = Modifier
                .constrainAs(uvIndexLayoutDividerConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(windLayoutConstrainedReference.bottom, 16.dp)
                    end.linkTo(parent.end)
                    width = Dimension.value(dp = 6.dp)
                    height = Dimension.value(dp = 6.dp)
                }
                .background(
                    color = MaterialTheme.colorScheme.surfaceTint,
                    shape = CircleShape
                )
        )

        // UV Index Layout
        Row(
            modifier = Modifier
                .constrainAs(uvIndexLayoutConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(uvIndexLayoutDividerConstrainedReference.bottom, 16.dp)
                    end.linkTo(parent.end)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp
            )
        ) {
            VerticalProgress(
                progress = (weatherResponseModel.currentWeatherDataModel?.uv?.toFloat()?.takeIf { it > 0.0 } ?: 0.1f) * 10,
                color = colorResource(
                    id = weatherResponseModel.currentWeatherDataModel?.uvIndexColor
                        ?: R.color.green
                ),
                modifier = Modifier
                    .height(
                        height = 100.dp
                    )
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp
                )
            ) {
                Text(
                    text = stringResource(id = R.string.key_uv_index_label),
                    style = Typography.displayMedium
                )
                Text(
                    text = stringResource(
                        id = R.string.key_uv_index_value_label,
                        weatherResponseModel.currentWeatherDataModel?.getUvIndexExposureLevel(
                            context = context
                        ) ?: "",
                        weatherResponseModel.currentWeatherDataModel?.uv?.toInt() ?: 0
                    ),
                    color = colorResource(
                        id = weatherResponseModel.currentWeatherDataModel?.uvIndexColor
                            ?: R.color.green
                    ),
                    textAlign = TextAlign.Center,
                    style = Typography.bodyMedium.medium
                )
            }
            val uvIndexLottieComposition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    resId = R.raw.sun_uv_animation
                )
            )
            val uvIndexLottieProgress by animateLottieCompositionAsState(
                uvIndexLottieComposition,
                iterations = LottieConstants.IterateForever,
                restartOnPlay = false
            )
            LottieAnimation(
                composition = uvIndexLottieComposition,
                progress = {
                    uvIndexLottieProgress
                },
                modifier = Modifier
                    .size(
                        size = 50.dp
                    )
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(humidityLayoutDividerConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(uvIndexLayoutConstrainedReference.bottom, 16.dp)
                    end.linkTo(parent.end)
                    width = Dimension.value(dp = 6.dp)
                    height = Dimension.value(dp = 6.dp)
                }
                .background(
                    color = MaterialTheme.colorScheme.surfaceTint,
                    shape = CircleShape
                )
        )

        // Humidity
        Row(
            modifier = Modifier
                .constrainAs(humidityLayoutConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(humidityLayoutDividerConstrainedReference.bottom, 16.dp)
                    end.linkTo(parent.end)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp
            )
        ) {
            CircularProgressbar(
                size = 80.dp,
                foregroundIndicatorColor = colorResource(id = R.color.fateBlue),
                dataUsage = weatherResponseModel.currentWeatherDataModel?.humidity?.toFloat() ?: 0f
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp
                )
            ) {
                Text(
                    text = stringResource(id = R.string.key_humidity_label),
                    style = Typography.displayMedium
                )
                Text(
                    text = stringResource(
                        id = R.string.key_value_with_percent_label,
                        weatherResponseModel.currentWeatherDataModel?.humidity ?: 0
                    ),
                    color = colorResource(
                        id = R.color.fateBlue
                    ),
                    textAlign = TextAlign.Center,
                    style = Typography.bodyMedium.medium
                )
            }
            val humidityLottieComposition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    resId = R.raw.humidity_animation
                )
            )
            val humidityLottieProgress by animateLottieCompositionAsState(
                humidityLottieComposition,
                iterations = LottieConstants.IterateForever,
                restartOnPlay = false
            )
            LottieAnimation(
                composition = humidityLottieComposition,
                progress = {
                    humidityLottieProgress
                },
                modifier = Modifier
                    .size(
                        size = 50.dp
                    )
            )
        }
    }
}

@Preview
@Composable
fun CurrentTemperatureRowLayoutPreview() {
    CurrentTemperatureRowLayout(
        weatherResponseModel = WeatherResponseModel()
    )
}