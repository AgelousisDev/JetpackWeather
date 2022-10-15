package com.agelousis.jetpackweather.weather.ui

import android.app.AlarmManager
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agelousis.jetpackweather.ui.rows.SelectionInputFieldRowLayout
import com.agelousis.jetpackweather.ui.rows.SwitchInputFieldRowLayout
import com.agelousis.jetpackweather.ui.theme.weatherBackgroundGradient
import com.agelousis.jetpackweather.utils.enumerations.LanguageEnum
import com.agelousis.jetpackweather.utils.extensions.*
import com.agelousis.jetpackweather.utils.helpers.PreferencesStoreHelper
import com.agelousis.jetpackweather.weather.WeatherActivity
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType
import com.agelousis.jetpackweather.weather.extensions.setAppLanguage
import com.agelousis.jetpackweather.weather.model.WeatherSettings
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SettingsLayout(
    viewModel: WeatherViewModel,
    contentPadding: PaddingValues
) {
    val context = LocalContext.current
    val preferencesStorageHelper = PreferencesStoreHelper(
        context = context
    )
    val scope = rememberCoroutineScope()
    val temperatureUnitType by preferencesStorageHelper.temperatureUnitType.collectAsState(
        initial = TemperatureUnitType.CELSIUS
    )
    val offlineMode by preferencesStorageHelper.offlineMode.collectAsState(
        initial = false
    )
    val weatherNotificationsState by preferencesStorageHelper.weatherNotificationsState.collectAsState(
        initial = false
    )
    val languageEnum by preferencesStorageHelper.languageEnum.collectAsState(
        initial = LanguageEnum.ENGLISH
    )

    val notificationsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        configureSwitchInputFieldEvent(
            context = context,
            scope = scope,
            preferencesStorageHelper = preferencesStorageHelper,
            weatherSettings = WeatherSettings.WeatherNotifications,
            isChecked = isGranted
        )
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = contentPadding.calculateTopPadding()
            )
            .background(
                brush = weatherBackgroundGradient()
            )
    ) {
        val (lazyColumnConstrainedReference) = createRefs()
        LazyColumn(
            modifier = Modifier
                .constrainAs(lazyColumnConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, 0.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            items(
                items = viewModel.weatherSettingsList
            ) { weatherSettings ->
                when(weatherSettings) {
                    WeatherSettings.TemperatureType,
                    WeatherSettings.WeatherLanguage -> {
                        SelectionInputFieldRowLayout(
                            weatherSettings = weatherSettings
                        ) { selectedPosition ->
                            configureSelectionInputFieldResult(
                                context = context,
                                scope = scope,
                                preferencesStorageHelper = preferencesStorageHelper,
                                weatherSettings = weatherSettings,
                                selectedPosition = selectedPosition
                            )
                        }
                        Divider(
                            thickness = 0.5.dp,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                )
                        )
                    }
                    WeatherSettings.OfflineMode,
                    WeatherSettings.WeatherNotifications -> {
                        SwitchInputFieldRowLayout(
                            weatherSettings = weatherSettings
                        ) { isChecked ->
                            if (weatherSettings is WeatherSettings.WeatherNotifications
                                && !context.arePermissionsGranted(android.Manifest.permission.POST_NOTIFICATIONS)
                                && isAndroid13
                            ) {
                                notificationsPermissionLauncher.launch(
                                    android.Manifest.permission.POST_NOTIFICATIONS
                                )
                                return@SwitchInputFieldRowLayout
                            }
                            configureSwitchInputFieldEvent(
                                context = context,
                                scope = scope,
                                preferencesStorageHelper = preferencesStorageHelper,
                                weatherSettings = weatherSettings,
                                isChecked = isChecked
                            )
                        }
                        Divider(
                            thickness = 0.5.dp,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                )
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(
        temperatureUnitType,
        offlineMode,
        weatherNotificationsState,
        languageEnum
    ) {
        viewModel.weatherSettingsList.clear()
        viewModel.weatherSettingsList.add(
            WeatherSettings.TemperatureType.with(
                context = context,
                temperatureUnitType = temperatureUnitType
            )
        )
        viewModel.weatherSettingsList.add(
            WeatherSettings.OfflineMode isEnabled offlineMode
        )
        viewModel.weatherSettingsList.add(
            WeatherSettings.WeatherNotifications isEnabled weatherNotificationsState
        )
        viewModel.weatherSettingsList.add(
            WeatherSettings.WeatherLanguage.with(
                context = context,
                languageEnum = languageEnum
            )
        )
    }
}

private fun configureSelectionInputFieldResult(
    context: Context,
    scope: CoroutineScope,
    preferencesStorageHelper: PreferencesStoreHelper,
    weatherSettings: WeatherSettings,
    selectedPosition: Int
) {
    when(weatherSettings) {
        WeatherSettings.TemperatureType ->
            scope.launch {
                preferencesStorageHelper setTemperatureUnitType TemperatureUnitType.values()[selectedPosition]
            }
        WeatherSettings.WeatherLanguage ->
            scope.launch {
                preferencesStorageHelper setLanguageEnum LanguageEnum.values()[selectedPosition]
                (context as? WeatherActivity)?.setAppLanguage(
                    languageEnum = LanguageEnum.values()[selectedPosition]
                )
            }
        else -> {}
    }
}

private fun configureSwitchInputFieldEvent(
    context: Context,
    scope: CoroutineScope,
    preferencesStorageHelper: PreferencesStoreHelper,
    weatherSettings: WeatherSettings,
    isChecked: Boolean
) {
    when(weatherSettings) {
        WeatherSettings.OfflineMode ->
            scope.launch {
                preferencesStorageHelper setOfflineMode isChecked
            }
        WeatherSettings.WeatherNotifications -> {
            scope.launch {
                preferencesStorageHelper setWeatherNotificationsState isChecked
            }
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