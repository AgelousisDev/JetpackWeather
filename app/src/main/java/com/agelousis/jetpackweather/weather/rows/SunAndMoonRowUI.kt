package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.network.response.WeatherAstroDataModel
import com.agelousis.jetpackweather.ui.rows.SunAndMoonStateLayout
import com.agelousis.jetpackweather.weather.enumerations.SunAndMoonState

@Composable
fun SunAndMoonRowLayout(
    sunAndMoonStates: List<SunAndMoonState>,
    weatherAstroDataModel: WeatherAstroDataModel?
) {
    weatherAstroDataModel ?: return
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
    ) {
        items(
            items = sunAndMoonStates
        ) { sunAndMoonState ->
            SunAndMoonStateLayout(
                sunAndMoonState = sunAndMoonState,
                weatherAstroDataModel = weatherAstroDataModel
            )
        }
    }
}

@Preview
@Composable
fun SunAndMoonStatePreview() {
    SunAndMoonRowLayout(
        sunAndMoonStates = SunAndMoonState.values().toList(),
        weatherAstroDataModel = WeatherAstroDataModel(
            sunrise = "07:00",
            sunset = "20:00",
            moonrise = "06:00",
            moonSet = "19:00",
            moonPhase = "",
            moonIllumination = "10"
        )
    )
}