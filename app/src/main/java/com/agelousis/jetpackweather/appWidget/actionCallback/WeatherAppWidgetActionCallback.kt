package com.agelousis.jetpackweather.appWidget.actionCallback

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.agelousis.jetpackweather.appWidget.WeatherAppWidget
import com.agelousis.jetpackweather.network.repositories.SuccessBlock
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.utils.extensions.jsonString
import com.agelousis.jetpackweather.utils.helpers.PreferencesStoreHelper
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class WeatherAppWidgetActionCallback : ActionCallback {

    private val scope = CoroutineScope(context = Dispatchers.Default)

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        requestWeatherForecast(
            context = context
        ) { weatherResponseModel ->
            scope.launch {
                updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) {
                    it.toMutablePreferences()
                        .apply {
                            this[PreferencesStoreHelper.WEATHER_RESPONSE_MODE_DATA_KEY] = weatherResponseModel.jsonString
                                ?: ""
                        }
                }
                WeatherAppWidget().update(context, glanceId)
            }
        }
    }

    private suspend fun requestWeatherForecast(
        context: Context,
        successBlock: SuccessBlock<WeatherResponseModel>
    ) {
        val preferencesStoreHelper = PreferencesStoreHelper(
            context = context
        )
        val addressDataModel = preferencesStoreHelper.currentAddressDataModel.firstOrNull()
        WeatherViewModel().requestCurrentWeather(
            context = context,
            location = "%f,%f".format(
                addressDataModel?.latitude
                    ?: return,
                addressDataModel.longitude
            ),
            airQualityState = true,
            successBlock = successBlock
        )
    }

}