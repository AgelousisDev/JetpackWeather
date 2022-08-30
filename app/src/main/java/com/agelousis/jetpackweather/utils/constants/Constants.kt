package com.agelousis.jetpackweather.utils.constants

object Constants {
    const val DISPLAY_DATE_TIME_FORMAT = "E, dd MMM yyyy HH:mm"
    const val DISPLAY_DATE_FORMAT = "E, dd MMM yyyy"
    const val SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm"
    const val SERVER_FULL_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    const val SERVER_DATE_FORMAT = "yyyy-MM-dd"
    const val SMALL_TIME_FORMAT = "hh:mm a"
    const val FULL_TIME_FORMAT = "HH:mm"

    object SharedPreferencesKeys {
        const val WEATHER_SHARED_PREFERENCES_KEY = "weatherSharedPreferences"
        const val TEMPERATURE_UNIT_TYPE_SHARED_PREFERENCES_KEY = "temperatureUnitType"
        const val OFFLINE_MODE_SHARED_PREFERENCES_KEY = "offlineMode"
        const val WEATHER_RESPONSE_DATA_SHARED_PREFERENCES_KEY = "weatherResponseModelData"
        const val WEATHER_NOTIFICATIONS_SHARED_PREFERENCES_KEY = "weatherNotifications"
        const val CURRENT_ADDRESS_DATA_SHARED_PREFERENCES_KEY = "currentAddressData"
    }

}