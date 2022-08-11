package com.agelousis.jetpackweather.mapAddressPicker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.agelousis.jetpackweather.mapAddressPicker.ui.MapAddressPickerActivityLayout
import com.agelousis.jetpackweather.mapAddressPicker.viewModel.MapViewModel
import com.agelousis.jetpackweather.ui.theme.JetpackWeatherTheme

class MapAddressPickerActivity: ComponentActivity() {

    companion object {
        const val CURRENT_ADDRESS = "MapAddressPickerActivity=currentAddress"
    }

    private val currentAddressDataModel by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent?.extras?.getParcelable(CURRENT_ADDRESS, AddressDataModel::class.java)
        else
            intent?.extras?.getParcelable(CURRENT_ADDRESS)
    }

    private val viewModel by viewModels<MapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        viewModel.addressDataModelMutableStateFlow.value = currentAddressDataModel
        viewModel.addressLine = currentAddressDataModel?.addressLine
        setContent {
            JetpackWeatherTheme {
                MapAddressPickerActivityLayout(
                    viewModel = viewModel
                )
            }
        }
    }

}