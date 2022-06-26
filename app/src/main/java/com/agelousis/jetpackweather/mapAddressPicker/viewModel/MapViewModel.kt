package com.agelousis.jetpackweather.mapAddressPicker.viewModel

import android.content.Context
import android.location.Geocoder
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.agelousis.jetpackweather.mapAddressPicker.AddressDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class MapViewModel: ViewModel()  {

    val addressDataModelMutableStateFlow = MutableStateFlow<AddressDataModel?>(
        value = null
    )
    val addressDataModelStateFlow = addressDataModelMutableStateFlow.asStateFlow()

    var addressLine by mutableStateOf(
        value = addressDataModelStateFlow.value?.addressLine
    )

    private var timer: CountDownTimer? = null

    fun onTextChanged(
        context: Context,
        text: String
    ) {
        if(text == "")
            return
        timer?.cancel()
        timer = object : CountDownTimer(1000, 1500) {
            override fun onTick(millisUntilFinished: Long) { }
            override fun onFinish() {
                addressDataModelMutableStateFlow.value = getLocationFromAddress(
                    context = context,
                    strAddress = text
                )
                addressLine = addressDataModelStateFlow.value?.addressLine
            }
        }.start()
    }

    fun getLocationFromAddress(context: Context, strAddress: String): AddressDataModel {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocationName(strAddress, 1)
        return AddressDataModel(
            countryName = addresses?.firstOrNull()?.countryName,
            countryCode = addresses?.firstOrNull()?.countryCode,
            longitude = addresses?.firstOrNull()?.longitude,
            latitude = addresses?.firstOrNull()?.latitude,
            addressLine = addresses?.firstOrNull()?.getAddressLine(0)
        )
    }

}