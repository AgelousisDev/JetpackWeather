package com.agelousis.jetpackweather.weather.rows

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.toDate
import com.agelousis.jetpackweather.utils.extensions.toDisplayDate
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.airbnb.lottie.compose.*
import java.util.*

@Composable
fun CalendarRowLayout(
    modifier: Modifier = Modifier,
    weatherNavigationScreen: WeatherNavigationScreen,
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
            text =
                when (weatherNavigationScreen) {
                    is WeatherNavigationScreen.Today ->
                        weatherResponseModel?.currentWeatherDataModel?.lastUpdated?.toDate(
                            pattern = Constants.SERVER_DATE_TIME_FORMAT
                        )?.toDisplayDate(
                            pattern = Constants.DISPLAY_DATE_TIME_FORMAT
                        )?.let {
                            stringResource(
                                id = R.string.key_last_updated_with_date_label,
                                it
                            )
                        } ?: ""
                    is WeatherNavigationScreen.Tomorrow ->
                        Date().toDisplayDate(
                            pattern = Constants.DISPLAY_DATE_TIME_FORMAT,
                            plusDays = 1
                        )
                    else -> ""
            },
            style = Typography.labelLarge,
            modifier = Modifier
                .constrainAs(dateTimeLabelConstrainedReference) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(imageConstrainedReference.top)
                    end.linkTo(imageConstrainedReference.start, 16.dp)
                    bottom.linkTo(imageConstrainedReference.bottom)
                    width = Dimension.fillToConstraints
                }
        )
        if (weatherResponseModel != null) {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    resId = weatherResponseModel.currentWeatherDataModel?.dayStateAnimationResourceId
                        ?: R.raw.day_animation
                )
            )
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever,
                restartOnPlay = false
            )
            LottieAnimation(
                composition = composition,
                progress = {
                    progress
                },
                modifier = Modifier
                    .constrainAs(imageConstrainedReference) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.value(
                            dp = 50.dp
                        )
                        height = Dimension.value(
                            dp = 50.dp
                        )
                    }
            )
        }
    }
}

@Preview
@Composable
fun CalendarRowLayoutPreview() {
    CalendarRowLayout(
        weatherNavigationScreen = WeatherNavigationScreen.Today,
        weatherResponseModel = null,
    )
}