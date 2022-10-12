package com.agelousis.jetpackweather.appWidget

import androidx.compose.runtime.Composable
import androidx.glance.wear.tiles.GlanceTileService
import com.agelousis.jetpackweather.appWidget.ui.WeatherAppWidgetAndWearTileLayout

class WeatherWearTileService: GlanceTileService() {

    @Composable
    override fun Content() {
        WeatherAppWidgetAndWearTileLayout()
    }

}