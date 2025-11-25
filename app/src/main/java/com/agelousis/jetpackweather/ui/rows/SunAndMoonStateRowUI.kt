package com.agelousis.jetpackweather.ui.rows

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.network.response.WeatherAstroDataModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.bold
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.fullTime
import com.agelousis.jetpackweather.utils.extensions.toDate
import com.agelousis.jetpackweather.weather.enumerations.SunAndMoonState

@Composable
fun SunAndMoonStateLayout(
    sunAndMoonState: SunAndMoonState,
    weatherAstroDataModel: WeatherAstroDataModel
) {
    Card(
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.surfaceTint
        ),
        shape = CircleShape
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(
                    width = 120.dp,
                    height = 120.dp
                )
        ) {
            Image(
                painter = painterResource(id = sunAndMoonState.icon),
                contentDescription = null,
                modifier = Modifier
                    .weight(
                        weight = 0.5f
                    )
                    .padding(
                        top = 16.dp
                    )
            )
            Text(
                text = stringResource(id = sunAndMoonState.labelResourceId),
                style = Typography.bodyMedium,
                modifier = Modifier
                    .weight(
                        weight = 0.2f
                    )
            )
            //Current Hour Box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(
                        weight = 0.3f
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
            ) {
                Text(
                    text = when(sunAndMoonState) {
                        SunAndMoonState.SUNRISE ->
                            weatherAstroDataModel.sunrise?.toDate(
                                pattern = Constants.SMALL_TIME_FORMAT
                            )?.fullTime ?: ""
                        SunAndMoonState.MOONRISE ->
                            weatherAstroDataModel.moonrise?.toDate(
                                pattern = Constants.SMALL_TIME_FORMAT
                            )?.fullTime ?: ""
                        SunAndMoonState.SUNSET ->
                            weatherAstroDataModel.sunset?.toDate(
                                pattern = Constants.SMALL_TIME_FORMAT
                            )?.fullTime ?: ""
                        SunAndMoonState.MOON_SET ->
                            weatherAstroDataModel.moonSet?.toDate(
                                pattern = Constants.SMALL_TIME_FORMAT
                            )?.fullTime ?: ""
                    },
                    style = Typography.bodyMedium.bold,
                    modifier = Modifier
                        .align(
                            alignment = Alignment.Center
                        )
                )
            }
        }
    }
}

@Preview
@Composable
fun SunAndMoonStateLayoutPreview() {
    SunAndMoonStateLayout(
        sunAndMoonState = SunAndMoonState.MOON_SET,
        weatherAstroDataModel = WeatherAstroDataModel(
            sunrise = "07:00 PM",
            sunset = "20:00 AM",
            moonrise = "06:00 PM",
            moonSet = "19:00 AM",
            moonPhase = "",
            moonIllumination = "10"
        )
    )
}