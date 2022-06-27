package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.textViewHeaderFont

@Composable
fun CurrentTemperatureRowLayout(
    weatherResponseModel: WeatherResponseModel?
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (temperatureLabelConstrainedReference, iconConstrainedReference,
            conditionConstrainedReference) = createRefs()
        Text(
            text = weatherResponseModel?.currentWeatherDataModel?.celciusTemperature ?: "",
            style = textViewHeaderFont,
            modifier = Modifier
                .constrainAs(temperatureLabelConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        Image(
            painter = rememberAsyncImagePainter(
                model = weatherResponseModel?.currentWeatherDataModel?.weatherConditionDataModel?.iconUrl
            ),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(iconConstrainedReference) {
                    top.linkTo(temperatureLabelConstrainedReference.top)
                    start.linkTo(temperatureLabelConstrainedReference.end, 16.dp)
                    width = Dimension.value(
                        dp = 35.dp
                    )
                    height = Dimension.value(
                        dp = 35.dp
                    )
                }
        )

        Text(
            text = weatherResponseModel?.currentWeatherDataModel?.weatherConditionDataModel?.text ?: "",
            style = Typography.bodyMedium,
            modifier = Modifier
                .constrainAs(conditionConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(iconConstrainedReference.bottom, 8.dp)
                    end.linkTo(parent.end)
                }
        )
    }
}