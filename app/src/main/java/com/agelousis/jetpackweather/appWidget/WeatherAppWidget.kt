package com.agelousis.jetpackweather.appWidget

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.Preferences
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text

class WeatherAppWidget: GlanceAppWidget() {

    override var stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val preferences = currentState<Preferences>()
        Text(text = "Hello World")
    }

}