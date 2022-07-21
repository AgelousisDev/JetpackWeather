package com.agelousis.jetpackweather.weather.ui

import android.app.AlarmManager
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agelousis.jetpackweather.ui.rows.SelectionInputFieldRowLayout
import com.agelousis.jetpackweather.ui.rows.SwitchInputFieldRowLayout
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.offlineMode
import com.agelousis.jetpackweather.utils.extensions.schedulePushNotificationsEvery
import com.agelousis.jetpackweather.utils.extensions.temperatureUnitType
import com.agelousis.jetpackweather.utils.extensions.weatherNotificationsState
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType
import com.agelousis.jetpackweather.weather.model.WeatherSettings
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

@Composable
fun SettingsLayout(
    viewModel: WeatherViewModel,
    contentPadding: PaddingValues
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(
        Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY,
        Context.MODE_PRIVATE
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = contentPadding.calculateTopPadding()
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            )
    ) {
        val (lazyColumnConstrainedReference) = createRefs()
        LazyColumn(
            modifier = Modifier
                .constrainAs(lazyColumnConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, 32.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            items(
                items = viewModel getWeatherSettings context
            ) { weatherSettings ->
                when(weatherSettings) {
                    is WeatherSettings.TemperatureType -> {
                        SelectionInputFieldRowLayout(
                            weatherSettings = weatherSettings
                        ) { selectedPosition ->
                            configureSelectionInputFieldResult(
                                sharedPreferences = sharedPreferences,
                                weatherSettings = weatherSettings,
                                selectedPosition = selectedPosition
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .height(
                                    height = 0.5.dp
                                )
                                .padding(
                                    start = 16.dp
                                )
                        )
                    }
                    is WeatherSettings.OfflineMode,
                    is WeatherSettings.WeatherNotifications -> {
                        SwitchInputFieldRowLayout(
                            weatherSettings = weatherSettings
                        ) { isChecked ->
                            configureSwitchInputFieldEvent(
                                context = context,
                                sharedPreferences = sharedPreferences,
                                weatherSettings = weatherSettings,
                                isChecked = isChecked
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .height(
                                    height = 0.5.dp
                                )
                                .padding(
                                    start = 16.dp
                                )
                        )
                    }
                }
            }
        }
    }
}

private fun configureSelectionInputFieldResult(
    sharedPreferences: SharedPreferences,
    weatherSettings: WeatherSettings,
    selectedPosition: Int
) {
    when(weatherSettings) {
        WeatherSettings.TemperatureType ->
            sharedPreferences.temperatureUnitType = TemperatureUnitType.values()[selectedPosition]
        else -> {}
    }
}

private fun configureSwitchInputFieldEvent(
    context: Context,
    sharedPreferences: SharedPreferences,
    weatherSettings: WeatherSettings,
    isChecked: Boolean
) {
    when(weatherSettings) {
        WeatherSettings.OfflineMode ->
            sharedPreferences.offlineMode = isChecked
        WeatherSettings.WeatherNotifications -> {
            sharedPreferences.weatherNotificationsState = isChecked
            context.schedulePushNotificationsEvery(
                scheduleState = isChecked,
                alarmManagerType = AlarmManager.INTERVAL_HOUR
            )
        }
        else -> {}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsLayoutPreview() {
    SettingsLayout(
        viewModel = viewModel(),
        contentPadding = PaddingValues()
    )
}