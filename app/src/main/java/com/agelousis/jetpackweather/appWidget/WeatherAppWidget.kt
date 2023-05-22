package com.agelousis.jetpackweather.appWidget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
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

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WeatherAppWidgetLayout()
        }
    }

    /*@Composable
    override fun Content() {
        WeatherAppWidgetLayout()
    }*/

}