package com.agelousis.jetpackweather.network.response

import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.toDate
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

data class WeatherForecastDataModel(
    @SerializedName(value = "forecastday") val weatherForecastDayDataModelList: List<WeatherForecastDayDataModel?>?
) {

    val currentWeatherForecastDayDataModel
        get() = weatherForecastDayDataModelList?.firstOrNull { weatherForecastDayDataModel ->
            LocalDate.
            weatherForecastDayDataModel?.date?.toDate(
                pattern = Constants.DATE_FORMAT
            ) == Date()
        }

}