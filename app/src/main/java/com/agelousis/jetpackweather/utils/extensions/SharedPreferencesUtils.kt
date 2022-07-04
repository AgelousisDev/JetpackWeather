package com.agelousis.jetpackweather.utils.extensions

import android.content.SharedPreferences
import androidx.core.content.edit
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType

var SharedPreferences.temperatureUnitType: TemperatureUnitType?
    set(value) {
        edit {
            putString(
                Constants.SharedPreferencesKeys.TEMPERATURE_UNIT_TYPE_SHARED_PREFERENCES_KEY,
                value?.name
            )
        }
    }
    get() = valueEnumOrNull(
        name = getString(
            Constants.SharedPreferencesKeys.TEMPERATURE_UNIT_TYPE_SHARED_PREFERENCES_KEY,
            null
        )
    )

var SharedPreferences.offlineMode: Boolean
    set(value) {
        edit {
            putBoolean(
                Constants.SharedPreferencesKeys.OFFLINE_MODE_SHARED_PREFERENCES_KEY,
                value
            )
        }
    }
    get() = getBoolean(
        Constants.SharedPreferencesKeys.OFFLINE_MODE_SHARED_PREFERENCES_KEY,
        false
    )