package com.agelousis.jetpackweather.utils.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.schedulePushNotificationsEvery
import com.agelousis.jetpackweather.utils.extensions.weatherNotificationsState

class WeatherBootReceiver: BroadcastReceiver() {

    companion object {
        private const val BOOT_COMPLETED_INTENT_ACTION = "android.intent.action.BOOT_COMPLETED"
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == BOOT_COMPLETED_INTENT_ACTION
            && p0?.getSharedPreferences(Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
                ?.weatherNotificationsState == true
        )
            p0.schedulePushNotificationsEvery(
                scheduleState = true,
                alarmManagerType = AlarmManager.INTERVAL_HOUR
            )
    }

}