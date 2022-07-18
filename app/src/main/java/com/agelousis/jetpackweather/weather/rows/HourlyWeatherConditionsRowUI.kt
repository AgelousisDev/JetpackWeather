package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.network.response.WeatherHourlyDataModel
import com.agelousis.jetpackweather.ui.rows.HourlyWeatherConditionRowLayout
import kotlinx.coroutines.delay

@Composable
fun HourlyWeatherConditionsRowLayout(
    modifier: Modifier = Modifier,
    weatherHourlyDataModelList: List<WeatherHourlyDataModel>
) {
    val lazyListState = rememberLazyListState()
    if (weatherHourlyDataModelList.size >= 24)
        LaunchedEffect(
            key1 = Unit
        ) {
            lazyListState.animateScrollToItem(
                index = 12
            )
        }
    LazyRow(
        modifier = modifier,
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
        contentPadding = PaddingValues(
            horizontal = 16.dp
        )
    ) {
        items(
            items = weatherHourlyDataModelList
        ) { weatherHourlyDataModel ->
            HourlyWeatherConditionRowLayout(
                weatherHourlyDataModel = weatherHourlyDataModel
            )
        }
    }
}

@Preview
@Composable
fun HourlyWeatherConditionsRowLayoutPreview() {
    HourlyWeatherConditionsRowLayout(
        weatherHourlyDataModelList = listOf()
    )
}