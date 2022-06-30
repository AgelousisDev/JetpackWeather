package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherResponseModel(
    @SerializedName(value = "location") val weatherLocationDataModel: WeatherLocationDataModel?,
    @SerializedName(value = "current") val currentWeatherDataModel: CurrentWeatherDataModel?,
    @SerializedName(value = "forecast") val weatherForecastDataModel: WeatherForecastDataModel?
)