package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.textViewHeaderFont

@Composable
fun CurrentTemperatureRowLayout(
    modifier: Modifier = Modifier,
    weatherResponseModel: WeatherResponseModel?
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
            windLayoutRow) = createRefs()
        Text(
            text = weatherResponseModel?.currentWeatherDataModel?.celsiusTemperature ?: "",
            style = textViewHeaderFont,
            modifier = Modifier
                .constrainAs(temperatureLabelConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        Image(
            painter = rememberAsyncImagePainter(
                model = weatherResponseModel?.currentWeatherDataModel?.weatherConditionDataModel?.iconUrl
            ),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(iconConstrainedReference) {
                    top.linkTo(temperatureLabelConstrainedReference.top)
                    start.linkTo(temperatureLabelConstrainedReference.end, 16.dp)
                    width = Dimension.value(
                        dp = 35.dp
                    )
                    height = Dimension.value(
                        dp = 35.dp
                    )
                }
        )

        Text(
            text = weatherResponseModel?.currentWeatherDataModel?.weatherConditionDataModel?.text ?: "",
            style = Typography.bodyMedium,
            color = colorResource(id = R.color.grey),
            modifier = Modifier
                .constrainAs(conditionConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(iconConstrainedReference.bottom, 8.dp)
                    end.linkTo(parent.end)
                }
        )

        if (weatherResponseModel != null)
            Text(
                text = stringResource(
                    id = R.string.key_feels_like_label,
                    weatherResponseModel.currentWeatherDataModel?.feelsLikeCelsiusTemperature ?: ""
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

        if (weatherResponseModel != null)
            Row(
                modifier = Modifier
                    .constrainAs(windLayoutRow) {
                        start.linkTo(parent.start)
                        top.linkTo(feelsLikeLabelConstrainedReference.bottom, 16.dp)
                        end.linkTo(parent.end)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp
                )
            ) {
                Text(
                    text = weatherResponseModel.currentWeatherDataModel?.windKph?.toInt()?.toString() ?: "",
                    style = textViewHeaderFont,
                    color = colorResource(
                        id = weatherResponseModel.currentWeatherDataModel?.windStateColor ?: R.color.grey
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
                            id = weatherResponseModel.currentWeatherDataModel?.windStateColor ?: R.color.grey
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
                Icon(
                    painter = painterResource(id = R.drawable.ic_wind),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier
                        .size(
                            size = 35.dp
                        )
                )
            }
    }
}

@Preview
@Composable
fun CurrentTemperatureRowLayoutPreview() {
    CurrentTemperatureRowLayout(
        weatherResponseModel = null
    )
}