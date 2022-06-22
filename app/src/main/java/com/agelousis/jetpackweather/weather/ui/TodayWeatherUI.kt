package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

@Composable
fun TodayWeatherLayout(
    viewModel: WeatherViewModel,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(
            count = 100
        ) { index ->
            Text(text = "Counter: $index")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TodayWeatherLayoutPreview() {
    TodayWeatherLayout(
        viewModel = WeatherViewModel(),
        contentPadding = PaddingValues()
    )
}