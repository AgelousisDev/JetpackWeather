package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherHourlyDataModel(
    @SerializedName(value = "time_epoch") val timeEpoch: Long?,
    @SerializedName(value = "time") val time: String?,
    @SerializedName(value = "temp_c") val tempC: Double?,
    @SerializedName(value = "temp_f") val tempF: Double?,
    @SerializedName(value = "is_day") val isDay: Int?,
    @SerializedName(value = "condition") val condition: WeatherConditionDataModel?,
    @SerializedName(value = "wind_mph") val windMph: Double?,
    @SerializedName(value = "wind_kph") val windKph: Double?,
    @SerializedName(value = "wind_degree") val windDegree: Double?,
    @SerializedName(value = "wind_dir") val windDir: String?,
    @SerializedName(value = "pressure_mb") val pressureMb: Double?,
    @SerializedName(value = "pressure_in") val pressureIn: Double?,
    @SerializedName(value = "precip_mm") val precipMm: Double?,
    @SerializedName(value = "precip_in") val precipIn: Double?,
    @SerializedName(value = "humidity") val humidity: Double?,
    @SerializedName(value = "cloud") val cloud: Double?,
    @SerializedName(value = "feelslike_c") val feelslikeC: Double?,
    @SerializedName(value = "feelslike_f") val feelslikeF: Double?,
    @SerializedName(value = "windchill_c") val windchillC: Double?,
    @SerializedName(value = "windchill_f") val windchillF: Double?,
    @SerializedName(value = "heatindex_c") val heatindexC: Double?,
    @SerializedName(value = "heatindex_f") val heatindexF: Double?,
    @SerializedName(value = "dewpoint_c") val dewpointC: Double?,
    @SerializedName(value = "dewpoint_f") val dewpointF: Double?,
    @SerializedName(value = "will_it_rain") val willItRain: Int?,
    @SerializedName(value = "chance_of_rain") val chanceOfRain: Int?,
    @SerializedName(value = "will_it_snow") val willItSnow: Int?,
    @SerializedName(value = "chance_of_snow") val chanceOfSnow: Int?,
    @SerializedName(value = "vis_km") val visKm: Double?,
    @SerializedName(value = "vis_miles") val visMiles: Double?,
    @SerializedName(value = "gust_mph") val gustMph: Double?,
    @SerializedName(value = "gust_kph") val gustKph: Double?,
    @SerializedName(value = "uv") val uv: Double?
)