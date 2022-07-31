package com.agelousis.jetpackweather.weather.viewModel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.mapAddressPicker.AddressDataModel
import com.agelousis.jetpackweather.network.repositories.SuccessBlock
import com.agelousis.jetpackweather.network.repositories.WeatherRepository
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.ui.models.HeaderModel
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.offlineMode
import com.agelousis.jetpackweather.utils.extensions.toDate
import com.agelousis.jetpackweather.utils.extensions.toDisplayDate
import com.agelousis.jetpackweather.utils.extensions.weatherResponseModel
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.agelousis.jetpackweather.weather.model.WeatherSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class WeatherViewModel: ViewModel() {

    var weatherUiAppBarTitle by mutableStateOf<String?>(
        value = null
    )
    var currentNavigationRoute by mutableStateOf(
        value = WeatherNavigationScreen.Today.route
    )

    private val loaderStateMutableStateFlow = MutableStateFlow(value = false)
    val loaderStateStateFlow: StateFlow<Boolean> = loaderStateMutableStateFlow.asStateFlow()

    private val networkErrorMutableStateFlow = MutableStateFlow(value = false)
    val networkErrorStateFlow: StateFlow<Boolean> = networkErrorMutableStateFlow.asStateFlow()

    private val _showDialog = MutableStateFlow(value = false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    val requestLocationMutableState = MutableStateFlow(value = false)
    val requestLocationState: StateFlow<Boolean> = requestLocationMutableState.asStateFlow()

    var alertPair by mutableStateOf<Pair<String?, String?>>(value = null to null)

    fun showDialog() {
        _showDialog.value = true
    }

    val swipeRefreshMutableStateFlow = MutableStateFlow(value = false)
    val swipeRefreshStateFlow: StateFlow<Boolean> = swipeRefreshMutableStateFlow.asStateFlow()

    fun onDialogConfirm() {
        alertPair = null to null
        _showDialog.value = false
        // Continue with executing the confirmed action
    }

    fun onDialogDismiss() {
        alertPair = null to null
        _showDialog.value = false
    }

    var locationPermissionState by mutableStateOf(
        value = false
    )
    val addressDataModelMutableStateFlow = MutableStateFlow<AddressDataModel?>(
        value = null
    )
    val addressDataModelStateFlow = addressDataModelMutableStateFlow.asStateFlow()

    private val weatherResponseMutableLiveData by lazy { MutableLiveData<WeatherResponseModel>() }
    val weatherResponseLiveData: LiveData<WeatherResponseModel>
        get() = weatherResponseMutableLiveData

    val nextDaysForecastDataList: List<Any>
        get() {
            val items = mutableListOf<Any>()
            for (weatherForecastDayDataModel in (weatherResponseLiveData.value?.weatherForecastDataModel?.nextWeatherForecastDayDataModelList ?: listOf())) {
                if (weatherForecastDayDataModel?.weatherHourlyDataModelList == null)
                    continue
                items.add(
                    HeaderModel(
                        header = weatherForecastDayDataModel.date?.toDate(
                            pattern = Constants.SERVER_DATE_FORMAT
                        )?.toDisplayDate(
                            pattern = Constants.DISPLAY_DATE_FORMAT
                        ) ?: ""
                    )
                )
                items.add(
                    weatherForecastDayDataModel.currentDayWeatherDataModel ?: continue
                )
                items.add(
                    weatherForecastDayDataModel.weatherHourlyDataModelList
                )
            }
            return items
        }

    infix fun getWeatherSettings(
        context: Context
    ) = listOf(
        WeatherSettings.TemperatureType withOptions context,
        WeatherSettings.OfflineMode isEnabled context,
        WeatherSettings.WeatherNotifications isEnabled context
    )

    fun getAddressFromLocation(
        context: Context,
        longitude: Double,
        latitude: Double
    ): AddressDataModel {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addresses: List<Address>? = null

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1)
        } catch(ex: Exception) {
            ex.printStackTrace()
        }

        return AddressDataModel(
            countryName = addresses?.firstOrNull()?.countryName,
            countryCode = addresses?.firstOrNull()?.countryCode,
            longitude = addresses?.firstOrNull()?.longitude,
            latitude = addresses?.firstOrNull()?.latitude,
            addressLine = addresses?.firstOrNull()?.getAddressLine(0)?.takeIf { addressLine ->
                addressLine.split(",").size < 3
            } ?: listOf(
                addresses?.firstOrNull()?.getAddressLine(0)?.split(",")?.getOrNull(index = 1) ?: "",
                addresses?.firstOrNull()?.getAddressLine(0)?.split(",")?.getOrNull(index = 1) ?: ""
            ).joinToString()
        )
    }

    fun requestCurrentWeather(
        context: Context,
        location: String,
        airQualityState: Boolean,
        successBlock: SuccessBlock<WeatherResponseModel>? = null
    ) {
        loaderStateMutableStateFlow.value = true
        WeatherRepository.requestCurrentWeather(
            location = location,
            airQualityState = airQualityState,
            successBlock = { weatherResponseModel ->
                swipeRefreshMutableStateFlow.value = false
                loaderStateMutableStateFlow.value = false
                networkErrorMutableStateFlow.value = false
                weatherResponseMutableLiveData.value = weatherResponseModel
                weatherUiAppBarTitle = weatherResponseModel.weatherLocationDataModel?.regionCountry
                successBlock?.invoke(weatherResponseModel)
            },
            failureBlock = {
                swipeRefreshMutableStateFlow.value = false
                loaderStateMutableStateFlow.value = false
                networkErrorMutableStateFlow.value = true
                alertPair = context.resources.getString(R.string.key_error_label) to it.localizedMessage
                showDialog()
            }
        )
    }

    fun requestForecast(
        context: Context,
        location: String,
        days: Int,
        airQualityState: Boolean,
        alertsState: Boolean
    ) {
        val sharedPreferences = context.getSharedPreferences(Constants.SharedPreferencesKeys.WEATHER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        loaderStateMutableStateFlow.value = !swipeRefreshStateFlow.value
        WeatherRepository.requestForecast(
            location = location,
            days = days,
            airQualityState = airQualityState,
            alertsState = alertsState,
            successBlock = { weatherResponseModel ->
                swipeRefreshMutableStateFlow.value = false
                loaderStateMutableStateFlow.value = false
                networkErrorMutableStateFlow.value = false
                weatherResponseMutableLiveData.value = weatherResponseModel
                weatherUiAppBarTitle = weatherResponseModel.weatherLocationDataModel?.regionCountry
                sharedPreferences.weatherResponseModel = weatherResponseModel
            },
            failureBlock = {
                swipeRefreshMutableStateFlow.value = false
                loaderStateMutableStateFlow.value = false
                if (sharedPreferences.offlineMode
                    && sharedPreferences.weatherResponseModel != null
                ) {
                    weatherResponseMutableLiveData.value = sharedPreferences.weatherResponseModel
                    weatherUiAppBarTitle = sharedPreferences.weatherResponseModel?.weatherLocationDataModel?.regionCountry
                }
                else
                    networkErrorMutableStateFlow.value = true
                //alertPair = context.resources.getString(R.string.key_error_label) to it.localizedMessage
                //onOpenDialogClicked()
            }
        )
    }

}