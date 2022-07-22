package com.agelousis.jetpackweather.utils.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.addressDataModel
import com.agelousis.jetpackweather.utils.helpers.NotificationHelper
import com.agelousis.jetpackweather.utils.helpers.UrlBitmapHelper
import com.agelousis.jetpackweather.utils.model.NotificationDataModel
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherBootReceiver: BroadcastReceiver() {

    companion object {
        private const val BOOT_COMPLETED_INTENT_ACTION = "android.intent.action.BOOT_COMPLETED"
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == BOOT_COMPLETED_INTENT_ACTION)
            p0?.getSharedPreferences(
                Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            )?.apply {
                val addressDataModel = addressDataModel ?: return@apply
                WeatherViewModel().requestCurrentWeather(
                    context = p0,
                    location = "%f,%f".format(
                        addressDataModel.latitude,
                        addressDataModel.longitude
                    ),
                    airQualityState = true
                ) { weatherResponseModel ->
                    openScopeAndScheduleNotification(
                        context = p0,
                        weatherResponseModel = weatherResponseModel
                    )
                }
            }
    }

    private fun openScopeAndScheduleNotification(
        context: Context,
        weatherResponseModel: WeatherResponseModel
    ) {
        scope.launch {
            UrlBitmapHelper.init(
                urlString = weatherResponseModel.currentWeatherDataModel?.weatherConditionDataModel?.iconUrl ?: return@launch
            ) { iconBitmap ->
                NotificationHelper.triggerNotification(
                    context = context,
                    notificationDataModel = NotificationDataModel(
                        notificationId = 1,
                        title = "%s - %s".format(
                            weatherResponseModel.weatherLocationDataModel?.regionCountry,
                            weatherResponseModel.currentWeatherDataModel.currentTemperatureUnitFormatted(
                                context = context
                            )
                        ),
                        body = weatherResponseModel.currentWeatherDataModel.weatherConditionDataModel.text,
                        largeImageBitmap = iconBitmap
                    )
                )
            }
        }
    }

}