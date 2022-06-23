package com.agelousis.jetpackweather.mapAddressPicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agelousis.jetpackweather.mapAddressPicker.ui.MapAddressPickerActivityLayout
import com.agelousis.jetpackweather.mapAddressPicker.viewModel.MapViewModel
import com.agelousis.jetpackweather.ui.theme.JetpackWeatherTheme

class MapAddressPickerActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackWeatherTheme {
                val viewModel = viewModel<MapViewModel>()
                MapAddressPickerActivityLayout(
                    viewModel = viewModel
                )
            }
        }
    }

}