package com.agelousis.jetpackweather.network.response

import android.content.Context
import com.agelousis.jetpackweather.R
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

data class CurrentWeatherDataModel(
    @SerializedName(value = "last_updated_epoch") val valpdatedEpoch: Long?,
    @SerializedName(value = "last_updated") val lastUpdated: String?,
    @SerializedName(value = "temp_c") val tempC: Double?,
    @SerializedName(value = "temp_f") val temp_f: Double?,
    @SerializedName(value = "is_day") val isDay: Int?,
    @SerializedName(value = "condition") val weatherConditionDataModel: WeatherConditionDataModel?,
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
) {

    val celsiusTemperature
        get() = "%d °C".format(
            tempC?.toInt() ?: 0
        )

    val feelsLikeCelsiusTemperature
        get() = "%d °C".format(
            feelslikeC?.toInt() ?: 0
        )

    val isDayBool
        get() = isDay == 1

    val windStateColor
        get() = when(windKph?.toInt() ?: 0) {
            in 0 until 20 ->
                R.color.blue
            in 20 until 30 ->
                R.color.teal_700
            in 30 until 50 ->
                R.color.green
            in 50 until 100 ->
                R.color.yellowDarker
            else ->
                R.color.red
        }

    fun getWindStateWarning(
        context: Context
    ): String = with(context.resources.getStringArray(R.array.key_wind_speed_warnings_array)) {
        when (windKph?.toInt() ?: 0) {
            in 0 until 20 ->
                first()
            in 20 until 30 ->
                this[1]
            in 30 until 50 ->
                this[2]
            in 50 until 100 ->
                this[3]
            else ->
                this[4]
        }
    }

    infix fun getWindDirection(
        context: Context
    ) = with(windDir) {
        val windDirectionsArray = context.resources.getStringArray(R.array.key_wind_directions_array)
        val windDirectionsBuilder = StringBuilder()
        for (windDirection in (windDir?.toCharArray()?.take(n = 2)?.distinct() ?: listOf()))
            windDirectionsBuilder.append(
                windDirectionsArray.firstOrNull {
                    it.startsWith(
                        prefix = windDirection.toString(),
                        ignoreCase = true
                    )
                }
            )
        windDirectionsBuilder.toString()
    }

}