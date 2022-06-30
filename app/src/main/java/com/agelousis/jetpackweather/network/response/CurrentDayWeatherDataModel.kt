package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class CurrentDayWeatherDataModel(
    @SerializedName(value = "maxtemp_c") val maxtempC: Double?,
    @SerializedName(value = "maxtemp_f") val maxtempF: Double?,
    @SerializedName(value = "mintemp_c") val mintempC: Double?,
    @SerializedName(value = "mintemp_f") val mintempF: Double?,
    @SerializedName(value = "avgtemp_c") val avgtempC: Double?,
    @SerializedName(value = "avgtemp_f") val avgtempF: Double?,
    @SerializedName(value = "maxwind_mph") val maxwindMph: Double?,
    @SerializedName(value = "maxwind_kph") val maxwindKph: Double?,
    @SerializedName(value = "totalprecip_mm") val totalprecipMm: Double?,
    @SerializedName(value = "totalprecip_in") val totalprecipIn: Double?,
    @SerializedName(value = "avgvis_km") val avgvisKm: Double?,
    @SerializedName(value = "avgvis_miles") val avgvisMiles: Double?,
    @SerializedName(value = "avghumidity") val avghumidity: Double?,
    @SerializedName(value = "daily_will_it_rain") val dailyWillItRain: Double?,
    @SerializedName(value = "daily_chance_of_rain") val dailyChanceOfRain: Double?,
    @SerializedName(value = "daily_will_it_snow") val dailyWillItSnow: Double?,
    @SerializedName(value = "daily_chance_of_snow") val dailyChanceOfSnow: Double?,
    @SerializedName(value = "condition") val weatherConditionDataModel: WeatherConditionDataModel?,
    @SerializedName(value = "uv") val uv: Double?
)