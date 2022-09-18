package com.agelousis.jetpackweather.appWidget

import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Dimension
import androidx.datastore.preferences.core.Preferences
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import coil.compose.rememberAsyncImagePainter
import com.agelousis.jetpackweather.appWidget.helper.CustomGlanceStateDefinition
import com.agelousis.jetpackweather.mapAddressPicker.AddressDataModel
import com.agelousis.jetpackweather.network.response.CurrentWeatherDataModel
import com.agelousis.jetpackweather.utils.extensions.toModel
import com.agelousis.jetpackweather.utils.extensions.urlBitmap
import com.agelousis.jetpackweather.utils.extensions.valueEnumOrNull
import com.agelousis.jetpackweather.utils.helpers.PreferencesStoreHelper
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType
import java.net.URL

class WeatherAppWidget: GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*>
        get() = CustomGlanceStateDefinition

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val preferences = currentState<Preferences>()
        val currentWeatherDataModel = remember {
            preferences[
                    PreferencesStoreHelper.WEATHER_RESPONSE_MODE_DATA_KEY
            ]?.toModel<CurrentWeatherDataModel>()
        }
        val addressDataModel = remember {
            preferences[
                PreferencesStoreHelper.CURRENT_ADDRESS_DATA_KEY
            ]?.toModel<AddressDataModel>()
        }
        val temperatureUnitType = remember {
            valueEnumOrNull<TemperatureUnitType>(
                name = preferences[PreferencesStoreHelper.TEMPERATURE_UNIT_TYPE_KEY]
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Text(
                text = currentWeatherDataModel?.currentTemperatureUnitFormatted(
                    temperatureUnitType = temperatureUnitType
                ) ?: "",
                style = TextStyle(
                    fontSize = 24.sp
                )
            )
            Image(
                provider = ImageProvider(
                    bitmap = currentWeatherDataModel?.weatherConditionDataModel?.iconUrl?.urlBitmap
                        ?: return@Column
                ),
                contentDescription = null,
                modifier = GlanceModifier
                    .padding(
                        top = 8.dp
                    )
            )
        }
    }

}