package com.agelousis.jetpackweather.weather.model

import android.content.Context
import androidx.annotation.StringRes
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.addressDataModel
import com.agelousis.jetpackweather.utils.extensions.offlineMode
import com.agelousis.jetpackweather.utils.extensions.weatherNotificationsState
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType

sealed class WeatherSettings(
    @StringRes val label: Int
) {
    var optionModelList: List<OptionModel>? = null
    var selectedOptionModel: OptionModel? = null
    var optionIsChecked = false
    var optionIsEnabled = true

    object TemperatureType: WeatherSettings(
        label = R.string.key_temperature_unit_label
    ) {

        /*infix fun withOptions(
            context: Context
        ) = this.apply {
            optionModelList = context.resources.getStringArray(R.array.key_temperature_unit_types_array).mapIndexed { index, item ->
                OptionModel(
                    label = item,
                    icon = if (index == 0) R.drawable.ic_celsius else R.drawable.ic_fahrenheit
                )
            }
            selectedOptionModel = context.getSharedPreferences(
                Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            )?.temperatureUnitType?.let { temperatureUnitType ->
                OptionModel(
                    label = context.resources.getStringArray(R.array.key_temperature_unit_types_array)[TemperatureUnitType.values().indexOf(temperatureUnitType)],
                    icon = temperatureUnitType.icon
                )
            }
        }*/

        fun with(
            context: Context,
            temperatureUnitType: TemperatureUnitType?
        ) = this.apply {
            optionModelList = context.resources.getStringArray(R.array.key_temperature_unit_types_array).mapIndexed { index, item ->
                OptionModel(
                    label = item,
                    icon = if (index == 0) R.drawable.ic_celsius else R.drawable.ic_fahrenheit
                )
            }
            selectedOptionModel = temperatureUnitType?.let {
                OptionModel(
                    label = context.resources.getStringArray(R.array.key_temperature_unit_types_array)[TemperatureUnitType.values().indexOf(it)],
                    icon = it.icon
                )
            }
        }

    }
    object OfflineMode: WeatherSettings(
        label = R.string.key_load_saved_weather_when_offline_label
    ) {

        infix fun isEnabled(
            context: Context
        ) = this.apply {
            optionIsChecked = context.getSharedPreferences(
                Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ).offlineMode
        }

    }

    object WeatherNotifications: WeatherSettings(
        label = R.string.key_weather_notifications_label
    ) {

        infix fun isEnabled(
            context: Context
        ) = this.apply {
            val sharedPreferences = context.getSharedPreferences(
                Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            )
            optionIsChecked = sharedPreferences.weatherNotificationsState
            optionIsEnabled = sharedPreferences.addressDataModel != null
        }

    }

}