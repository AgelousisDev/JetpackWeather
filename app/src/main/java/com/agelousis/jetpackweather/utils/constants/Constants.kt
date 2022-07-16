package com.agelousis.jetpackweather.utils.constants

object Constants {
    const val DISPLAY_DATE_TIME_FORMAT = "E, dd MMM yyyy HH:mm"
    const val SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm"
    const val SMALL_TIME_FORMAT = "hh:mm a"
    const val FULL_TIME_FORMAT = "HH:mm"

    object SharedPreferencesKeys {
        const val WEATHER_SHARED_PREFERENCES_KEY = "weatherSharedPreferences"
        const val TEMPERATURE_UNIT_TYPE_SHARED_PREFERENCES_KEY = "temperatureUnitType"
        const val OFFLINE_MODE_SHARED_PREFERENCES_KEY = "offlineMode"
    }

}