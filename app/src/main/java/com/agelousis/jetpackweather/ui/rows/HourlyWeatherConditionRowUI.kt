package com.agelousis.jetpackweather.ui.rows

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
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
                    width = 100.dp
                )
                .padding(
                    all = 16.dp
                )
        ) {
            //Current Hour
            Text(
                text = weatherHourlyDataModel.time ?: "",
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
        }
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