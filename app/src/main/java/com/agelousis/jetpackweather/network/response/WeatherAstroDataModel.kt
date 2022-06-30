package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherAstroDataModel(
    @SerializedName(value = "sunrise") val sunrise: String?,
    @SerializedName(value = "sunset") val sunset: String?,
    @SerializedName(value = "moonrise") val moonrise: String?,
    @SerializedName(value = "moonset") val moonSet: String?,
    @SerializedName(value = "moon_phase") val moonPhase: String?,
    @SerializedName(value = "moon_illumination") val moonIllumination: String?
)