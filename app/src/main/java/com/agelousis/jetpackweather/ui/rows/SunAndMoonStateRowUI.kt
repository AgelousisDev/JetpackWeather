package com.agelousis.jetpackweather.ui.rows

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.WeatherAstroDataModel
import com.agelousis.jetpackweather.ui.theme.Typography
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
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(
                    width = 100.dp
                )
                .padding(
                    all = 16.dp
                )
        ) {
            Image(
                painter = painterResource(id = sunAndMoonState.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(
                        size = 35.dp
                    )
            )
            Text(
                text = stringResource(id = sunAndMoonState.labelResourceId),
                style = Typography.bodyMedium
            )
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
                style = Typography.labelMedium,
                color = colorResource(id = R.color.grey)
            )
        }
    }
}

@Preview
@Composable
fun SunAndMoonStateLayoutPreview() {
    SunAndMoonStateLayout(
        sunAndMoonState = SunAndMoonState.MOON_SET,
        weatherAstroDataModel = WeatherAstroDataModel(
            sunrise = "07:00",
            sunset = "20:00",
            moonrise = "06:00",
            moonSet = "19:00",
            moonPhase = "",
            moonIllumination = "10"
        )
    )
}