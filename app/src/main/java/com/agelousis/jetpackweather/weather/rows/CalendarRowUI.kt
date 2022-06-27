package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.utils.extensions.toDisplayDate
import java.util.*
import com.agelousis.jetpackweather.R
import kotlinx.coroutines.delay

@Composable
fun CalendarRowLayout(
    weatherResponseModel: WeatherResponseModel?
) {
    var displayDateTime by remember {
        mutableStateOf(
            value = Date().toDisplayDate()
        )
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (dateTimeLabelConstrainedReference, imageConstrainedReference) = createRefs()
        Text(
            text = displayDateTime,
            style = Typography.displayLarge,
            modifier = Modifier
                .constrainAs(dateTimeLabelConstrainedReference) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(imageConstrainedReference.top)
                    end.linkTo(imageConstrainedReference.start, 16.dp)
                    bottom.linkTo(imageConstrainedReference.bottom)
                    width = Dimension.fillToConstraints
                }
        )
        Image(
            painter = painterResource(
                id = if (weatherResponseModel?.currentWeatherDataModel?.isDayBool == true) R.drawable.ic_sun else R.drawable.ic_moon
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
    LaunchedEffect(
        key1 = Unit
    ) {
        while (true) {
            delay(
                timeMillis = 1000
            )
            displayDateTime = Date().toDisplayDate()
        }
    }
}