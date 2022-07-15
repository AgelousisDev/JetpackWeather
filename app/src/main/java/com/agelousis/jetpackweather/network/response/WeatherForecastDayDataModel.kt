package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherForecastDayDataModel(
    @SerializedName(value = "date") val date: String?,
    @SerializedName(value = "date_epoch") val dateEpoch: String?,
    @SerializedName(value = "day") val currentDayWeatherDataModel: CurrentDayWeatherDataModel?,
    @SerializedName(value = "astro") val weatherAstroDataModel: WeatherAstroDataModel?,
    @SerializedName(value = "hour") val weatherHourlyDataModelList: List<WeatherHourlyDataModel>?
)