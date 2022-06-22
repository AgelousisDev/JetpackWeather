package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

@Composable
fun TomorrowWeatherLayout(
    viewModel: WeatherViewModel,
    contentPadding: PaddingValues
) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TomorrowWeatherLayoutPreview() {
    TomorrowWeatherLayout(
        viewModel = WeatherViewModel(),
        contentPadding = PaddingValues()
    )
}