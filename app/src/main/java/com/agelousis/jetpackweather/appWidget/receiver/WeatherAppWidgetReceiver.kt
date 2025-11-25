package com.agelousis.jetpackweather.appWidget.receiver

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.agelousis.jetpackweather.appWidget.WeatherAppWidget

class WeatherAppWidgetReceiver: GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = WeatherAppWidget()

}