package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherResponseModel(
    @SerializedName(value = "location") val weatherLocationDataModel: WeatherLocationDataModel? = null,
    @SerializedName(value = "current") val currentWeatherDataModel: CurrentWeatherDataModel? = null,
    @SerializedName(value = "forecast") val weatherForecastDataModel: WeatherForecastDataModel? = null
)