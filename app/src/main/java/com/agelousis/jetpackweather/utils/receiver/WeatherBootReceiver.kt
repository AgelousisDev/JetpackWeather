package com.agelousis.jetpackweather.utils.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.weatherResponseModel
import com.agelousis.jetpackweather.utils.helpers.NotificationHelper
import com.agelousis.jetpackweather.utils.model.NotificationDataModel

class WeatherBootReceiver: BroadcastReceiver() {

    companion object {
        private const val BOOT_COMPLETED_INTENT_ACTION = "android.intent.action.BOOT_COMPLETED"
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == BOOT_COMPLETED_INTENT_ACTION)
            p0?.getSharedPreferences(
                Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            )?.apply {
                NotificationHelper.triggerNotification(
                    context = p0,
                    notificationDataModel = NotificationDataModel(
                        notificationId = 1,
                        title = p0.resources.getString(R.string.app_name),
                        body = weatherResponseModel?.currentWeatherDataModel?.currentTemperatureUnitFormatted(
                            context = p0
                        )
                    )
                )
            }
    }

}