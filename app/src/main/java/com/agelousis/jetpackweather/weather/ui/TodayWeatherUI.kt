package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.ui.rows.HeaderRowLayout
import com.agelousis.jetpackweather.weather.rows.CalendarRowLayout
import com.agelousis.jetpackweather.weather.rows.CurrentTemperatureRowLayout
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.ui.models.HeaderModel
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.agelousis.jetpackweather.weather.rows.HourlyWeatherConditionsRowLayout
import com.agelousis.jetpackweather.weather.rows.SunAndMoonRowLayout
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun TodayWeatherLayout(
    viewModel: WeatherViewModel,
    contentPadding: PaddingValues
) {
    val loaderState by viewModel.loaderStateStateFlow.collectAsState()
    val weatherResponseModel by viewModel.weatherResponseLiveData.observeAsState()
    val isRefreshing by viewModel.swipeRefreshStateFlow.collectAsState()
    val networkErrorState by viewModel.networkErrorStateFlow.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = contentPadding.calculateTopPadding()
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            )
    ) {
        val (lazyColumnConstrainedReference, progressIndicatorConstrainedReference,
            networkErrorAnimationConstrainedReference) = createRefs()
        if (!networkErrorState)
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = {
                    viewModel.swipeRefreshMutableStateFlow.value = true
                },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        // Pass the SwipeRefreshState + trigger through
                        state = state,
                        refreshTriggerDistance = trigger,
                        // Enable the scale animation
                        scale = true,
                        // Change the color and shape
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                        contentColor = Color.White
                    )
                },
                modifier = Modifier
                    .constrainAs(lazyColumnConstrainedReference) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, 8.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 16.dp
                    ),
                    contentPadding = PaddingValues(
                        bottom = 170.dp
                    )
                ) {
                    item {
                        CalendarRowLayout(
                            modifier = Modifier
                                .animateItemPlacement(),
                            weatherNavigationScreen = WeatherNavigationScreen.Today,
                            weatherResponseModel = weatherResponseModel
                        )
                    }
                    item {
                        CurrentTemperatureRowLayout(
                            modifier = Modifier
                                .animateItemPlacement(),
                            currentWeatherDataModel = weatherResponseModel?.currentWeatherDataModel ?: return@item
                        )
                    }
                    item {
                        if (weatherResponseModel != null)
                            HeaderRowLayout(
                                modifier = Modifier
                                    .animateItemPlacement(),
                                headerModel = HeaderModel(
                                    header = stringResource(id = R.string.key_sun_and_moon_label)
                                )
                            )
                    }
                    item {
                        SunAndMoonRowLayout(
                            modifier = Modifier
                                .animateItemPlacement(),
                            sunAndMoonStates = weatherResponseModel?.weatherForecastDataModel?.currentWeatherForecastDayDataModel?.weatherAstroDataModel?.availableSunAndMoonStates ?: listOf(),
                            weatherAstroDataModel = weatherResponseModel?.weatherForecastDataModel?.currentWeatherForecastDayDataModel?.weatherAstroDataModel
                        )
                    }
                    item {
                        if (weatherResponseModel != null
                            && weatherResponseModel?.weatherForecastDataModel?.currentWeatherForecastDayDataModel?.remainingWeatherHourlyDataModelList?.isNotEmpty() == true
                        )
                            HeaderRowLayout(
                                modifier = Modifier
                                    .animateItemPlacement(),
                                headerModel = HeaderModel(
                                    header = stringResource(id = R.string.key_temperature_label)
                                )
                            )
                    }
                    item {
                        HourlyWeatherConditionsRowLayout(
                            modifier = Modifier
                                .animateItemPlacement(),
                            weatherHourlyDataModelList = weatherResponseModel?.weatherForecastDataModel?.currentWeatherForecastDayDataModel?.remainingWeatherHourlyDataModelList
                                ?: listOf()
                        )
                    }
                }
            }
        if (loaderState && !isRefreshing)
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(progressIndicatorConstrainedReference) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

        NetworkErrorLayout(
            state = networkErrorState,
            modifier = Modifier
                .constrainAs(networkErrorAnimationConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clickable {
                    viewModel.swipeRefreshMutableStateFlow.value = true
                }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TodayWeatherLayoutPreview() {
    TodayWeatherLayout(
        viewModel = WeatherViewModel(),
        contentPadding = PaddingValues()
    )
}