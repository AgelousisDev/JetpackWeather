package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.utils.extensions.toDisplayDate
import java.util.*
import com.agelousis.jetpackweather.R

@Composable
fun CalendarRowLayout(
    modifier: Modifier = Modifier,
    weatherResponseModel: WeatherResponseModel?
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                other = modifier
            )
    ) {
        val (dateTimeLabelConstrainedReference, imageConstrainedReference) = createRefs()
        Text(
            text = Date().toDisplayDate(),
            style = Typography.labelMedium,
            modifier = Modifier
                .constrainAs(dateTimeLabelConstrainedReference) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(imageConstrainedReference.top)
                    end.linkTo(imageConstrainedReference.start, 16.dp)
                    bottom.linkTo(imageConstrainedReference.bottom)
                    width = Dimension.fillToConstraints
                }
        )
        if (weatherResponseModel != null)
            Image(
                painter = painterResource(
                    id = if (weatherResponseModel.currentWeatherDataModel?.isDayBool == true) R.drawable.ic_sun else R.drawable.ic_moon
                ),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(imageConstrainedReference) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.value(
                            dp = 40.dp
                        )
                        height = Dimension.value(
                            dp = 40.dp
                        )
                    }
            )
    }
}

@Preview
@Composable
fun CalendarRowLayoutPreview() {
    CalendarRowLayout(
        weatherResponseModel = null
    )
}