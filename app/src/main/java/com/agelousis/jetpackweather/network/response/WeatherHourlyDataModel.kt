package com.agelousis.jetpackweather.network.response

import android.content.Context
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.temperatureUnitType
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType
import com.google.gson.annotations.SerializedName

data class WeatherHourlyDataModel(
    @SerializedName(value = "time_epoch") val timeEpoch: Long? = null,
    @SerializedName(value = "time") val time: String? = null,
    @SerializedName(value = "temp_c") val tempC: Double? = null,
    @SerializedName(value = "temp_f") val tempF: Double? = null,
    @SerializedName(value = "is_day") val isDay: Int? = null,
    @SerializedName(value = "condition") val weatherConditionDataModel: WeatherConditionDataModel? = null,
    @SerializedName(value = "wind_mph") val windMph: Double? = null,
    @SerializedName(value = "wind_kph") val windKph: Double? = null,
    @SerializedName(value = "wind_degree") val windDegree: Double? = null,
    @SerializedName(value = "wind_dir") val windDir: String? = null,
    @SerializedName(value = "pressure_mb") val pressureMb: Double? = null,
    @SerializedName(value = "pressure_in") val pressureIn: Double? = null,
    @SerializedName(value = "precip_mm") val precipMm: Double? = null,
    @SerializedName(value = "precip_in") val precipIn: Double? = null,
    @SerializedName(value = "humidity") val humidity: Double? = null,
    @SerializedName(value = "cloud") val cloud: Double? = null,
    @SerializedName(value = "feelslike_c") val feelslikeC: Double? = null,
    @SerializedName(value = "feelslike_f") val feelslikeF: Double? = null,
    @SerializedName(value = "windchill_c") val windchillC: Double? = null,
    @SerializedName(value = "windchill_f") val windchillF: Double? = null,
    @SerializedName(value = "heatindex_c") val heatindexC: Double? = null,
    @SerializedName(value = "heatindex_f") val heatindexF: Double? = null,
    @SerializedName(value = "dewpoint_c") val dewpointC: Double? = null,
    @SerializedName(value = "dewpoint_f") val dewpointF: Double? = null,
    @SerializedName(value = "will_it_rain") val willItRain: Int? = null,
    @SerializedName(value = "chance_of_rain") val chanceOfRain: Int? = null,
    @SerializedName(value = "will_it_snow") val willItSnow: Int? = null,
    @SerializedName(value = "chance_of_snow") val chanceOfSnow: Int? = null,
    @SerializedName(value = "vis_km") val visKm: Double? = null,
    @SerializedName(value = "vis_miles") val visMiles: Double? = null,
    @SerializedName(value = "gust_mph") val gustMph: Double? = null,
    @SerializedName(value = "gust_kph") val gustKph: Double? = null,
    @SerializedName(value = "uv") val uv: Double? = null
) {

    fun currentTemperatureUnitFormatted(context: Context) =
        when(context.getSharedPreferences(Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).temperatureUnitType) {
            TemperatureUnitType.FAHRENHEIT ->
                "%d °F".format(
                    tempF?.toInt() ?: 0
                )
            else ->
                "%d °C".format(
                    tempC?.toInt() ?: 0
                )
        }

}