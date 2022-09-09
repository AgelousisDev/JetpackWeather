package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.WeatherAirQualityDataModel
import com.agelousis.jetpackweather.ui.composableView.RangeLayout
import com.agelousis.jetpackweather.ui.theme.Typography

@Composable
fun AirQualityRowLayout(
    modifier: Modifier = Modifier,
    weatherAirQualityDataModel: WeatherAirQualityDataModel
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
        modifier = modifier
    ) {
        (weatherAirQualityDataModel getAirQualityData context).forEach { item ->
            Text(
                text = item,
                style = Typography.bodyMedium,
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp
                    )
                    .fillMaxWidth()
            )
        }
        RangeLayout(
            modifier = Modifier
                .height(
                    height = 60.dp
                )
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            width = LocalConfiguration.current.screenWidthDp.dp - 32.dp,
            colors = listOf(
                colorResource(id = R.color.green),
                colorResource(id = R.color.lightBlue),
                colorResource(id = R.color.yellowLighter),
                colorResource(id = R.color.yellowDarker),
                colorResource(id = R.color.orange),
                colorResource(id = R.color.red)
            ),
            labels = stringArrayResource(id = R.array.key_air_quality_us_epa_index_array).toList(),
            selectedColorIndex = weatherAirQualityDataModel.usEpaIndex?.let {
                it - 1
            }
        )
    }
}