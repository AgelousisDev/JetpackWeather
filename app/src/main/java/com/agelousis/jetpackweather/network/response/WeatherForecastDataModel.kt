package com.agelousis.jetpackweather.network.response

import com.agelousis.jetpackweather.utils.extensions.toDate
import com.google.gson.annotations.SerializedName
import java.util.*

data class WeatherForecastDataModel(
    @SerializedName(value = "forecastday") val weatherForecastDayDataModelList: List<WeatherForecastDayDataModel?>?
) {

    val currentWeatherForecastDayDataModel
        get() = weatherForecastDayDataModelList?.firstOrNull { weatherForecastDayDataModel ->
            weatherForecastDayDataModel?.date?.toDate() == Date()
        }

}