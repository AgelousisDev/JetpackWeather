package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.CurrentDayWeatherDataModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.textViewHeaderFont
import com.agelousis.jetpackweather.utils.helpers.PreferencesStoreHelper
import com.agelousis.jetpackweather.weather.enumerations.TemperatureUnitType
import com.airbnb.lottie.compose.*

@Composable
fun ForecastDayWeatherLayout(
    modifier: Modifier = Modifier,
    currentDayWeatherDataModel: CurrentDayWeatherDataModel
) {
    val context = LocalContext.current
    val preferencesStoreHelper = PreferencesStoreHelper(
        context = context
    )
    val temperatureUnitType by preferencesStoreHelper.temperatureUnitType.collectAsState(
        initial = TemperatureUnitType.CELSIUS
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .then(
                other = modifier
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp
            )
        ) {
            // C
            Text(
                text = currentDayWeatherDataModel currentMinMaxTemperatureUnitFormatted temperatureUnitType,
                style = textViewHeaderFont
            )

            // Condition Icon
            Image(
                painter = rememberAsyncImagePainter(
                    model = currentDayWeatherDataModel.weatherConditionDataModel?.iconUrl
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(
                        size = 40.dp
                    )
            )
        }

        // Condition Text
        Text(
            text = currentDayWeatherDataModel.weatherConditionDataModel?.text ?: "",
            style = Typography.bodyMedium,
            color = colorResource(id = R.color.grey)
        )

        // Wind Layout
        Row(
            modifier = Modifier
                .padding(
                    top = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp
            )
        ) {
            Text(
                text = currentDayWeatherDataModel.maxWindKph?.toInt()?.toString()
                    ?: "",
                style = textViewHeaderFont,
                color = colorResource(
                    id = currentDayWeatherDataModel.windStateColor
                )
            )
            Text(
                text = currentDayWeatherDataModel.getWindStateWarning(
                    context = context
                ),
                style = Typography.displayMedium,
                color = colorResource(
                    id = currentDayWeatherDataModel.windStateColor
                )
            )
            val windLottieComposition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    resId = R.raw.wind_animation
                )
            )
            val windLottieProgress by animateLottieCompositionAsState(
                windLottieComposition,
                iterations = LottieConstants.IterateForever,
                restartOnPlay = false
            )
            LottieAnimation(
                composition = windLottieComposition,
                progress = {
                    windLottieProgress
                },
                modifier = Modifier
                    .size(
                        size = 50.dp
                    )
            )
        }
    }
}