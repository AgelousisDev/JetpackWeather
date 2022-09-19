package com.agelousis.jetpackweather.appWidget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.appWidget.helper.CustomGlanceStateDefinition
import com.agelousis.jetpackweather.mapAddressPicker.AddressDataModel
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.*
import com.agelousis.jetpackweather.utils.helpers.PreferencesStoreHelper
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType
import java.util.*

class WeatherAppWidget: GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*>
        get() = CustomGlanceStateDefinition

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val preferences = currentState<Preferences>()
        val weatherResponseModel = remember {
            preferences[
                    PreferencesStoreHelper.WEATHER_RESPONSE_MODE_DATA_KEY
            ]?.toModel<WeatherResponseModel>()
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier
                .fillMaxSize()
                .background(
                    colorProvider = ColorProvider(
                        R.color.whiteTwo
                    )
                )
                .cornerRadius(
                    radius = 32.dp
                )
        ) {
            // Location & Time
            Text(
                text = "${addressDataModel?.addressLine}\n${Date().toDisplayDate(
                    pattern = Constants.DISPLAY_DATE_TIME_FORMAT
                )}",
                style = TextStyle(
                    fontSize = 14.sp
                ),
                modifier = GlanceModifier
                    .padding(
                        top = 16.dp
                    )
            )
            Row(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically,
                modifier = GlanceModifier
                    .padding(
                        top = 16.dp
                    )
            ) {
                // C
                Text(
                    text = weatherResponseModel?.currentWeatherDataModel?.currentTemperatureUnitFormatted(
                        temperatureUnitType = temperatureUnitType
                    ) ?: "",
                    style = TextStyle(
                        fontSize = 24.sp
                    )
                )
                val iconBitmap = weatherResponseModel?.currentWeatherDataModel?.weatherConditionDataModel?.iconUrl?.urlBitmap
                if (iconBitmap != null)
                    // Condition Icon
                    Image(
                        provider = ImageProvider(
                            bitmap = iconBitmap
                        ),
                        contentDescription = null,
                        modifier = GlanceModifier
                            .padding(
                                start = 16.dp
                            )
                            .size(
                                size = 40.dp
                            )
                    )
            }
            // Condition Text
            Text(
                text = weatherResponseModel?.currentWeatherDataModel?.weatherConditionDataModel?.text
                    ?: "",
                style = TextStyle(
                    color = ColorProvider(R.color.grey),
                    fontSize = 14.sp
                ),
                modifier = GlanceModifier
                    .padding(
                        top = 8.dp
                    )
            )
            // Feels Like
            Text(
                text = context.resources.getString(
                    R.string.key_feels_like_label,
                    weatherResponseModel?.currentWeatherDataModel?.feelsLikeTemperatureUnitFormatted(
                        temperatureUnitType = temperatureUnitType
                    ) ?: ""
                ),
                style = TextStyle(
                    color = ColorProvider(R.color.grey),
                    fontSize = 14.sp
                ),
                modifier = GlanceModifier
                    .padding(
                        top = 8.dp
                    )
            )
            Spacer(
                modifier = GlanceModifier
                    .padding(
                        top = 8.dp
                    )
            )
            // Wind Layout
            Row(
                modifier = GlanceModifier
                    .padding(
                        top = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weatherResponseModel?.currentWeatherDataModel?.windKph?.toInt()?.toString()
                        ?: "",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = ColorProvider(
                            resId = weatherResponseModel?.currentWeatherDataModel?.windStateColor
                                ?: R.color.colorAccent
                        )
                    )
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier
                        .padding(
                            start = 16.dp
                        )
                ) {
                    if (weatherResponseModel?.currentWeatherDataModel?.windDegree != null)
                        Image(
                            provider = ImageProvider(
                                resId = R.drawable.ic_arrow_direction_down
                            ),
                            contentDescription = null,
                            modifier = GlanceModifier
                                .size(
                                    size = 15.dp
                                )
                        )
                    Text(
                        text = context.resources.getString(R.string.key_km_hourly_label),
                        style = TextStyle(
                            color = ColorProvider(R.color.grey),
                            fontSize = 12.sp,
                        ),
                        modifier = GlanceModifier
                            .padding(
                                top = 8.dp
                            )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier
                        .padding(
                            start = 16.dp
                        )
                ) {
                    Text(
                        text = weatherResponseModel?.currentWeatherDataModel?.getWindStateWarning(
                            context = context
                        ) ?: "",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = ColorProvider(
                                resId = weatherResponseModel?.currentWeatherDataModel?.windStateColor
                                    ?: R.color.colorAccent
                            )
                        )
                    )
                    Text(
                        text = context.resources.getString(
                            R.string.key_now_with_value_label,
                            weatherResponseModel?.currentWeatherDataModel?.getWindDirection(
                                context = context
                            ) ?: ""
                        ),
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }

}