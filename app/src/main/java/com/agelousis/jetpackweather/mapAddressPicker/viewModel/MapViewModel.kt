package com.agelousis.jetpackweather.mapAddressPicker.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.agelousis.jetpackweather.mapAddressPicker.AddressDataModel
import com.agelousis.jetpackweather.utils.helpers.LocationHelper
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModel: ViewModel()  {

    val addressDataModelMutableStateFlow = MutableStateFlow<AddressDataModel?>(
        value = null
    )
    val addressDataModelStateFlow = addressDataModelMutableStateFlow.asStateFlow()

    var addressLine by mutableStateOf(
        value = addressDataModelStateFlow.value?.addressLine
    )
    var addressIsLoading by mutableStateOf(
        value = false
    )

    fun onTextChanged(
        context: Context,
        text: String,
        invalidAddressStateBlock: () -> Unit
    ) {
        if (text == "")
            return
        LocationHelper.getLocationFromAddress(
            context = context,
            strAddress = text
        ) { addressDataModel ->
            addressDataModel?.let { unwrappedAddressDataModel ->
                addressDataModelMutableStateFlow.value = unwrappedAddressDataModel
                addressLine = unwrappedAddressDataModel.addressLine
            } ?: run {
                addressLine = ""
                invalidAddressStateBlock()
            }
        }
    }

    fun getAddressFromLatLng(
        context: Context,
        latLng: LatLng
    ) {
        addressIsLoading = true
        LocationHelper.getAddressFromLocation(
            context = context,
            longitude = latLng.longitude,
            latitude = latLng.latitude
        ) { addressDataModel ->
            addressDataModelMutableStateFlow.value = addressDataModel
            addressLine = addressDataModel?.addressLine
            addressIsLoading = false
        }
    }

}