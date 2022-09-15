package com.agelousis.jetpackweather.appWidget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.Preferences
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.addressDataModel
import com.agelousis.jetpackweather.utils.extensions.weatherResponseModel

class WeatherAppWidget: GlanceAppWidget() {

    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val preferences = currentState<Preferences>()
        val weatherPair = getWeatherPair(
            context = context
        )
        Text(text = "Hello World")
    }

    private fun getWeatherPair(
        context: Context
    ) = context.getSharedPreferences(
        Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY,
        Context.MODE_PRIVATE
    )?.let { sharedPreferences ->
        val addressDataModel = sharedPreferences.addressDataModel
        sharedPreferences.weatherResponseModel?.weatherForecastDataModel?.todayWeatherForecastDayDataModel?.currentWeatherDataModel?.let { currentWeatherDataModel ->
            currentWeatherDataModel to addressDataModel
        }
    }

}