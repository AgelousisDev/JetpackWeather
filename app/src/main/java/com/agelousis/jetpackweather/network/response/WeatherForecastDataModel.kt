package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherForecastDataModel(
    @SerializedName(value = "forecastday") val weatherForecastDayDataModelList: List<WeatherForecastDayDataModel?>?
) {

    val currentWeatherForecastDayDataModel
        get() = weatherForecastDayDataModelList?.firstOrNull()

    val nextWeatherForecastDayDataModel
        get() = weatherForecastDayDataModelList?.getOrNull(
            index = 1
        )

    val nextWeatherForecastDayDataModelList
        get() = weatherForecastDayDataModelList?.drop(
            n = 1
        )

}