package com.agelousis.jetpackweather.appWidget

import androidx.compose.runtime.Composable
import androidx.glance.action.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.state.GlanceStateDefinition
import com.agelousis.jetpackweather.appWidget.helper.CustomGlanceStateDefinition
import com.agelousis.jetpackweather.appWidget.ui.WeatherAppWidgetLayout

class WeatherAppWidget: GlanceAppWidget() {

    companion object {
        private const val APP_START_ACTION_PARAM = "appStartActionParam"
        val appStartActionParam = ActionParameters.Key<Boolean>(name = APP_START_ACTION_PARAM)
    }

    override val stateDefinition: GlanceStateDefinition<*>
        get() = CustomGlanceStateDefinition


    @Composable
    override fun Content() {
        WeatherAppWidgetLayout()
    }

}