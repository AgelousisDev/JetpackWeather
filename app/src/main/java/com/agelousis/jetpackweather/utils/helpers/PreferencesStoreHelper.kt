package com.agelousis.jetpackweather.utils.helpers

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.agelousis.jetpackweather.mapAddressPicker.AddressDataModel
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.utils.extensions.jsonString
import com.agelousis.jetpackweather.utils.extensions.toModel
import com.agelousis.jetpackweather.utils.extensions.valueEnumOrNull
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType
import kotlinx.coroutines.flow.map

class PreferencesStoreHelper(val context: Context) {

    companion object {
        private const val WEATHER_PREFERENCES_KEY = "weatherPreferences"
        private val Context.dataStore by preferencesDataStore(name = WEATHER_PREFERENCES_KEY)

        private val TEMPERATURE_UNIT_TYPE_KEY = stringPreferencesKey(name = "temperatureUnitType")
        private val OFFLINE_MODE_KEY = booleanPreferencesKey(name = "offlineMode")
        private val WEATHER_RESPONSE_MODE_DATA_KEY = stringPreferencesKey(name = "weatherResponseModelData")
        private val WEATHER_NOTIFICATION_STATE_KEY = booleanPreferencesKey(name = "weatherNotifications")
        private val CURRENT_ADDRESS_DATA_KEY = stringPreferencesKey(name = "currentAddressData")
    }

    suspend infix fun setTemperatureUnitType(temperatureUnitType: TemperatureUnitType) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[TEMPERATURE_UNIT_TYPE_KEY] = temperatureUnitType.name
        }
    }

    val temperatureUnitType
        get() = context.dataStore.data.map { preferences ->
            valueEnumOrNull<TemperatureUnitType>(name = preferences[TEMPERATURE_UNIT_TYPE_KEY] ?: TemperatureUnitType.CELSIUS.name)
        }

    suspend infix fun setOfflineMode(offlineMode: Boolean) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[OFFLINE_MODE_KEY] = offlineMode
        }
    }

    val offlineMode
        get() = context.dataStore.data.map { preferences ->
            preferences[OFFLINE_MODE_KEY] == true
        }

    suspend infix fun setWeatherResponseModelData(weatherResponseModel: WeatherResponseModel) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[WEATHER_RESPONSE_MODE_DATA_KEY] = weatherResponseModel.jsonString
                ?: ""
        }
    }

    val weatherResponseModelData
        get() = context.dataStore.data.map { preferences ->
            preferences[WEATHER_RESPONSE_MODE_DATA_KEY]?.takeIf {
                it.isNotEmpty()
            }?.toModel<WeatherResponseModel>()
        }

    suspend infix fun setWeatherNotificationsState(state: Boolean) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[WEATHER_NOTIFICATION_STATE_KEY] = state
        }
    }

    val weatherNotificationsState
        get() = context.dataStore.data.map { preferences ->
            preferences[WEATHER_NOTIFICATION_STATE_KEY] == true
        }

    suspend infix fun setCurrentAddressData(addressDataModel: AddressDataModel) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[CURRENT_ADDRESS_DATA_KEY] = addressDataModel.jsonString
                ?: ""
        }
    }

    val currentAddressDataModel
        get() = context.dataStore.data.map { preferences ->
            preferences[CURRENT_ADDRESS_DATA_KEY]?.takeIf {
                it.isNotEmpty()
            }?.toModel<AddressDataModel>()
        }

}