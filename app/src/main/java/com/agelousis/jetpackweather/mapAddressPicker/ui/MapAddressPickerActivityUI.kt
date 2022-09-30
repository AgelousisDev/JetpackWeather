package com.agelousis.jetpackweather.mapAddressPicker.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.agelousis.jetpackweather.mapAddressPicker.viewModel.MapViewModel

@Composable
fun MapAddressPickerActivityLayout(
    viewModel: MapViewModel
) {
    MapAddressPickerTopAppBar(
        viewModel = viewModel
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapAddressPickerActivityLayoutPreview() {
    MapAddressPickerActivityLayout(
        viewModel = MapViewModel()
    )
}