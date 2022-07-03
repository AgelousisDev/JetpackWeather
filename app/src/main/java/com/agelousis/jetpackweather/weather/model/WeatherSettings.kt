package com.agelousis.jetpackweather.weather.model

import android.content.Context
import androidx.annotation.StringRes
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.weather.enumerations.WeatherOptionInputType

sealed class WeatherSettings(
    @StringRes val label: Int,
    val weatherOptionInputType: WeatherOptionInputType
) {
    var optionModelList: List<OptionModel>? = null

    object TemperatureType: WeatherSettings(
        label = R.string.key_temperature_unit_label,
        weatherOptionInputType = WeatherOptionInputType.DROPDOWN
    ) {

        infix fun withOptions(
            context: Context
        ) = this.apply {
            optionModelList = context.resources.getStringArray(R.array.key_temperature_unit_types_array).mapIndexed { index, item ->
                OptionModel(
                    label = item,
                    icon = if (index == 0) R.drawable.ic_celsius else R.drawable.ic_fahrenheit
                )
            }
        }

    }
    object OfflineMode: WeatherSettings(
        label = R.string.key_load_saved_weather_when_offline_label,
        weatherOptionInputType = WeatherOptionInputType.SWITCH
    )
}