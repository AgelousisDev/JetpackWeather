package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherForecastDataModel(
    @SerializedName(value = "forecastday") val weatherForecastDayDataModelList: List<WeatherForecastDayDataModel?>?
) {

    val currentWeatherForecastDayDataModel
        get() = weatherForecastDayDataModelList?.firstOrNull()

}