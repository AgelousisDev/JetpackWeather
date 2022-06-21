package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherConditionDataModel(
    @SerializedName(value = "text") val text: String?,
    @SerializedName(value = "icon") val icon: String?,
    @SerializedName(value = "code") val code: Int?
)