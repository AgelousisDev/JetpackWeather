package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherAirQualityDataModel(
    @SerializedName(value = "co") val co: Double?,
    @SerializedName(value = "no2") val no2: Double?,
    @SerializedName(value = "o3") val o3: Double?,
    @SerializedName(value = "so2") val so2: Double?,
    @SerializedName(value = "pm2_5") val pm25: Double?,
    @SerializedName(value = "pm10") val pm10: Double?,
    @SerializedName(value = "us-epa-index") val usEpaIndex: Double?,
    @SerializedName(value = "gb-defra-index") val gbDefraIndex: Double?
)