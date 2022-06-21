package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDataModel(
    @SerializedName(value = "last_updated_epoch") val valpdatedEpoch: Long?,
    @SerializedName(value = "last_updated") val lastUpdated: String?,
    @SerializedName(value = "temp_c") val tempC: Double?,
    @SerializedName(value = "temp_f") val temp_f: Double?,
    @SerializedName(value = "is_day") val isDay: Int?,
    @SerializedName(value = "condition") val condition: WeatherConditionDataModel?,
    @SerializedName(value = "wind_mph") val windMph: Double?,
    @SerializedName(value = "wind_kph") val windKph: Double?,
    @SerializedName(value = "wind_degree") val windDegree: Int?,
    @SerializedName(value = "wind_dir") val windDir: String?,
    @SerializedName(value = "pressure_mb") val pressureMb: Double?,
    @SerializedName(value = "pressure_in") val pressureIn: Double?,
    @SerializedName(value = "precip_mm") val precipMm: Double?,
    @SerializedName(value = "precip_in") val precipIn: Double?,
    @SerializedName(value = "humidity") val humidity: Int?,
    @SerializedName(value = "cloud") val cloud: Int?,
    @SerializedName(value = "feelslike_c") val feelslikeC: Double?,
    @SerializedName(value = "feelslike_f") val feelslikeF: Double?,
    @SerializedName(value = "vis_km") val visKm: Double?,
    @SerializedName(value = "vis_miles") val visMiles: Double?,
    @SerializedName(value = "uv") val uv: Double?,
    @SerializedName(value = "gust_mph") val gustMph: Double?,
    @SerializedName(value = "gust_kph") val gustKph: Double?,
    @SerializedName(value = "air_quality") val airQuality: WeatherAirQualityDataModel?,
)