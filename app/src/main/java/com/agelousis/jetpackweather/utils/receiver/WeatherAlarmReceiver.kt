package com.agelousis.jetpackweather.utils.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.mapAddressPicker.AddressDataModel
import com.agelousis.jetpackweather.network.response.CurrentWeatherDataModel
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.addressDataModel
import com.agelousis.jetpackweather.utils.extensions.weatherResponseModel
import com.agelousis.jetpackweather.utils.helpers.NotificationHelper
import com.agelousis.jetpackweather.utils.helpers.UrlBitmapHelper
import com.agelousis.jetpackweather.utils.model.NotificationDataModel
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class WeatherAlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val NOTIFICATION_ID_EXTRA = "WeatherAlarmReceiver=notificationIdExtra"
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onReceive(p0: Context?, p1: Intent?) {
        p0 ?: return
        p1?.extras?.getInt(NOTIFICATION_ID_EXTRA)?.let { notificationId ->
            cancelNotificationAndOpenTheApp(
                context = p0,
                notificationId = notificationId
            )
        } ?: p0.getSharedPreferences(
            Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )?.apply {
            val addressDataModel = addressDataModel ?: return@apply
            weatherResponseModel?.weatherForecastDataModel?.todayWeatherForecastDayDataModel?.currentWeatherDataModel?.let { currentWeatherDataModel ->
                openScopeAndScheduleNotification(
                    context = p0,
                    addressDataModel = addressDataModel,
                    currentWeatherDataModel = currentWeatherDataModel
                )
            }
                ?: WeatherViewModel().requestCurrentWeather(
                    context = p0,
                    location = "%f,%f".format(
                        addressDataModel.latitude,
                        addressDataModel.longitude
                    ),
                    airQualityState = true
                ) { weatherResponseModel ->
                    openScopeAndScheduleNotification(
                        context = p0,
                        addressDataModel = addressDataModel,
                        currentWeatherDataModel = weatherResponseModel.currentWeatherDataModel
                            ?: return@requestCurrentWeather
                    )
                }
        }
    }

    private fun openScopeAndScheduleNotification(
        context: Context,
        addressDataModel: AddressDataModel,
        currentWeatherDataModel: CurrentWeatherDataModel
    ) {
        scope.launch {
            UrlBitmapHelper.init(
                urlString = currentWeatherDataModel.weatherConditionDataModel?.iconUrl
                    ?: return@launch
            ) { iconBitmap ->
                val notificationId = Random.nextInt()
                NotificationHelper.triggerNotification(
                    context = context,
                    notificationDataModel = NotificationDataModel(
                        notificationId = notificationId,
                        title = addressDataModel.addressLine,
                        body = "%s\n%s\n%s\n%s\n%s".format(
                            currentWeatherDataModel.currentTemperatureUnitFormatted(
                                context = context
                            ),
                            currentWeatherDataModel.weatherConditionDataModel.text,
                            "%s %s".format(
                                currentWeatherDataModel.windKph?.toInt()
                                    ?.toString()
                                    ?: "",
                                context.resources.getString(R.string.key_km_hourly_label)
                            ),
                            currentWeatherDataModel.getWindStateWarning(
                                context = context
                            ),
                            context.resources.getString(
                                R.string.key_now_with_value_label,
                                currentWeatherDataModel.getWindDirection(
                                    context = context
                                )
                            )
                        ),
                        largeImageBitmap = iconBitmap,
                        buttons = listOf(
                            Triple(
                                first = R.drawable.ic_update,
                                second = context.resources.getString(R.string.key_update_location_label),
                                third = PendingIntent.getBroadcast(
                                    context,
                                    notificationId,
                                    Intent(
                                        context,
                                        WeatherAlarmReceiver::class.java
                                    ).also { intent ->
                                        intent.putExtra(NOTIFICATION_ID_EXTRA, notificationId)
                                    },
                                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
                                )
                            )
                        )
                    )
                )
            }
        }
    }

    private fun cancelNotificationAndOpenTheApp(
        context: Context,
        notificationId: Int
    ) {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.cancel(
            notificationId
        )
        context.startActivity(
            Intent(context.packageManager.getLaunchIntentForPackage(context.packageName)).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            }
        )
    }

}