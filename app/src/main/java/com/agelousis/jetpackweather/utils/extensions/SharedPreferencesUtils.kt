package com.agelousis.jetpackweather.utils.extensions

import android.content.SharedPreferences
import androidx.core.content.edit
import com.agelousis.jetpackweather.mapAddressPicker.AddressDataModel
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType
import com.google.gson.Gson

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

var SharedPreferences.weatherResponseModel: WeatherResponseModel?
    set(value) {
        edit {
            putString(
                Constants.SharedPreferencesKeys.WEATHER_RESPONSE_DATA_SHARED_PREFERENCES_KEY,
                value?.let {
                    Gson().toJson(it)
                }
            )
        }
    }
    get() =
        try {
            getString(
                Constants.SharedPreferencesKeys.WEATHER_RESPONSE_DATA_SHARED_PREFERENCES_KEY,
                null
            )?.takeIf {
                it.isNotEmpty()
            }?.let { weatherResponseData ->
                Gson().fromJson(
                    weatherResponseData,
                    WeatherResponseModel::class.java
                )
            }
        }
        catch (e: Exception) {
            null
        }

var SharedPreferences.weatherNotificationsState: Boolean
    set(value) {
        edit {
            putBoolean(
                Constants.SharedPreferencesKeys.WEATHER_NOTIFICATIONS_SHARED_PREFERENCES_KEY,
                value
            )
        }
    }
    get() = getBoolean(
        Constants.SharedPreferencesKeys.WEATHER_NOTIFICATIONS_SHARED_PREFERENCES_KEY,
        false
    )

var SharedPreferences.addressDataModel: AddressDataModel?
    set(value) {
        edit {
            putString(
                Constants.SharedPreferencesKeys.CURRENT_ADDRESS_DATA_SHARED_PREFERENCES_KEY,
                value?.let {
                    Gson().toJson(it)
                }
            )
        }
    }
    get() =
        try {
            getString(
                Constants.SharedPreferencesKeys.CURRENT_ADDRESS_DATA_SHARED_PREFERENCES_KEY,
                null
            )?.takeIf {
                it.isNotEmpty()
            }?.let { addressData ->
                Gson().fromJson(
                    addressData,
                    AddressDataModel::class.java
                )
            }
        }
        catch (e: Exception) {
            null
        }