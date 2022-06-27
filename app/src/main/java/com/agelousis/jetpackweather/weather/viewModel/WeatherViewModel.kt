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
import com.agelousis.jetpackweather.network.repositories.WeatherRepository
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class WeatherViewModel: ViewModel() {

    private val loaderStateMutableStateFlow = MutableStateFlow(value = false)
    val loaderStateStateFlow: StateFlow<Boolean> = loaderStateMutableStateFlow.asStateFlow()

    private val _showDialog = MutableStateFlow(value = false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    var alertPair by mutableStateOf<Pair<String?, String?>>(value = null to null)

    private fun onOpenDialogClicked() {
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