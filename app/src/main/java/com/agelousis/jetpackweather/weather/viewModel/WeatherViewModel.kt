package com.agelousis.jetpackweather.weather.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.repositories.WeatherRepository
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel: ViewModel() {

    private val loaderStateMutableStateFlow = MutableStateFlow(value = false)
    val loaderStateStateFlow: StateFlow<Boolean> = loaderStateMutableStateFlow.asStateFlow()

    private val _showDialog = MutableStateFlow(value = false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    var alertPair by mutableStateOf<Pair<String?, String?>>(value = null to null)

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        alertPair = null to null
        _showDialog.value = false
        // Continue with executing the confirmed action
    }

    fun onDialogDismiss() {
        alertPair = null to null
        _showDialog.value = false
    }

    private val weatherResponseMutableLiveData by lazy { MutableLiveData<WeatherResponseModel>() }
    val weatherResponseLiveData: LiveData<WeatherResponseModel>
        get() = weatherResponseMutableLiveData

    fun requestCurrentWeather(
        context: Context,
        location: String,
        airQualityState: Boolean
    ) {
        loaderStateMutableStateFlow.value = true
        WeatherRepository.requestCurrentWeather(
            location = location,
            airQualityState = airQualityState,
            successBlock = { weatherResponseModel ->
                loaderStateMutableStateFlow.value = false
                weatherResponseMutableLiveData.value = weatherResponseModel
            },
            failureBlock = {
                loaderStateMutableStateFlow.value = false
                alertPair = context.resources.getString(R.string.key_error_label) to it.localizedMessage
                onOpenDialogClicked()
            }
        )
    }

}