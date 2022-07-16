package com.agelousis.jetpackweather.ui.rows

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.WeatherHourlyDataModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.textViewHeaderFont

@Composable
fun HourlyWeatherConditionRowLayout(
    weatherHourlyDataModel: WeatherHourlyDataModel
) {
    val context = LocalContext.current
    Card(
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.surfaceTint
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(
                    width = 130.dp
                )
                .padding(
                    all = 16.dp
                )
        ) {
            //Current Hour
            Text(
                text = weatherHourlyDataModel.displayTime ?: "",
                style = Typography.bodyMedium
            )
            // Condition Icon
            Image(
                painter = rememberAsyncImagePainter(
                    model = weatherHourlyDataModel.weatherConditionDataModel?.iconUrl
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(
                        size = 40.dp
                    )
            )
            // C
            Text(
                text = weatherHourlyDataModel.currentTemperatureUnitFormatted(
                    context = context
                ),
                style = textViewHeaderFont
            )
            // Condition Text
            Text(
                text = weatherHourlyDataModel.weatherConditionDataModel?.text ?: "",
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.grey),
                textAlign = TextAlign.Center
            )

            // Feels Like
            Text(
                text = stringResource(
                    id = R.string.key_feels_like_label,
                    weatherHourlyDataModel.feelsLikeTemperatureUnitFormatted(
                        context = context
                    )
                ),
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.grey),
                textAlign = TextAlign.Center
            )

            // Wind Layout
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp
                ),
                modifier = Modifier
                    .padding(
                        top = 16.dp
                    )
            ) {
                Text(
                    text = weatherHourlyDataModel.windKph?.toInt()
                        ?.toString()
                        ?: "",
                    style = textViewHeaderFont,
                    color = colorResource(
                        id = weatherHourlyDataModel.windStateColor
                    )
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 8.dp
                    )
                ) {
                    if (weatherHourlyDataModel.windDegree != null)
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
                                    degrees = weatherHourlyDataModel.windDegree.toFloat()
                                )
                        )
                    Text(
                        text = stringResource(id = R.string.key_km_hourly_label),
                        style = Typography.labelMedium,
                        color = colorResource(id = R.color.grey)
                    )
                }
            }

            Text(
                text = weatherHourlyDataModel.getWindStateWarning(
                    context = context
                ),
                style = Typography.displayMedium,
                color = colorResource(
                    id = weatherHourlyDataModel.windStateColor
                )
            )
            Text(
                text = stringResource(
                    id = R.string.key_now_with_value_label,
                    weatherHourlyDataModel.getWindDirection(
                        context = context
                    )
                ),
                style = Typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        // End of Wind Layout
    }
}

@Preview
@Composable
fun HourlyWeatherConditionRowLayoutPreview() {
    HourlyWeatherConditionRowLayout(
        weatherHourlyDataModel = WeatherHourlyDataModel(
            tempC = 20.0,
            tempF = 30.0,
            humidity = 60.0,
            uv = 8.0
        )
    )
}