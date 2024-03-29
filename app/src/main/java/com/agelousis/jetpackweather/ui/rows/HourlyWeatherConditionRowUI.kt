package com.agelousis.jetpackweather.ui.rows

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import coil.compose.rememberAsyncImagePainter
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.WeatherHourlyDataModel
import com.agelousis.jetpackweather.ui.composableView.MarqueeText
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.bold
import com.agelousis.jetpackweather.ui.theme.textViewHeaderFont
import com.agelousis.jetpackweather.utils.helpers.PreferencesStoreHelper
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType

@Composable
fun HourlyWeatherConditionRowLayout(
    weatherHourlyDataModel: WeatherHourlyDataModel
) {
    val context = LocalContext.current
    val preferencesStoreHelper = PreferencesStoreHelper(
        context = context
    )
    val temperatureUnitType by preferencesStoreHelper.temperatureUnitType.collectAsState(
        initial = TemperatureUnitType.CELSIUS
    )
    Card(
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.surfaceTint
        )
    ) {
        Column(
            modifier = Modifier
                .width(
                    width = 130.dp
                )
        ) {
            //Current Hour Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        height = 30.dp
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
            ) {
                Text(
                    text = weatherHourlyDataModel.displayTime ?: "",
                    style = Typography.bodyMedium.bold,
                    modifier = Modifier
                        .align(
                            alignment = Alignment.Center
                        )
                )
            }

            // Main Content
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            ) {
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
                    text = weatherHourlyDataModel currentTemperatureUnitFormatted temperatureUnitType,
                    style = textViewHeaderFont
                )
                // Condition Text
                MarqueeText(
                    text = weatherHourlyDataModel.weatherConditionDataModel?.text ?: "",
                    style = Typography.bodyMedium,
                    color = colorResource(id = R.color.grey),
                    textAlign = TextAlign.Center
                )

                // Feels Like
                MarqueeText(
                    text = stringResource(
                        id = R.string.key_feels_like_label,
                        weatherHourlyDataModel feelsLikeTemperatureUnitFormatted temperatureUnitType
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
                // End of Wind Layout
            }
        }
    }
}

@Preview
@Composable
fun HourlyWeatherConditionRowLayoutPreview() {
    HourlyWeatherConditionRowLayout(
        weatherHourlyDataModel = WeatherHourlyDataModel(
            time = "2022-07-24 20:00",
            tempC = 20.0,
            tempF = 30.0,
            humidity = 60,
            uv = 8.0
        )
    )
}