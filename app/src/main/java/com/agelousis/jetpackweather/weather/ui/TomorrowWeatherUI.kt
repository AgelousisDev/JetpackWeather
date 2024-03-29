package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.ui.composableView.FullScreenLottieLayout
import com.agelousis.jetpackweather.ui.models.HeaderModel
import com.agelousis.jetpackweather.ui.rows.HeaderRowLayout
import com.agelousis.jetpackweather.ui.theme.weatherBackgroundGradient
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.agelousis.jetpackweather.weather.rows.*
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

@Composable
fun TomorrowWeatherLayout(
    viewModel: WeatherViewModel,
    contentPadding: PaddingValues
) {
    val loaderState by viewModel.loaderStateStateFlow.collectAsState()
    val weatherResponseModel by viewModel.weatherResponseLiveData.observeAsState()
    val isRefreshing by viewModel.swipeRefreshStateFlow.collectAsState()
    val networkErrorState by viewModel.networkErrorStateFlow.collectAsState()
    val requestLocationState by viewModel.requestLocationState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = viewModel::isRefreshing
    )
    val lazyColumnState = rememberLazyListState()
    val firstVisibleItemIndex by remember {
        derivedStateOf {
            lazyColumnState.firstVisibleItemIndex == 0
        }
    }
    LaunchedEffect(
        key1 = firstVisibleItemIndex
    ) {
        viewModel.lazyColumnFirstChildVisibilityMutableStateFlow.value = firstVisibleItemIndex
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = contentPadding.calculateTopPadding()
            )
            .background(
                brush = weatherBackgroundGradient()
            )
    ) {
        val (lazyColumnConstrainedReference, progressIndicatorConstrainedReference,
            networkErrorAnimationConstrainedReference) = createRefs()
        if (!networkErrorState)
            Box(
                modifier = Modifier
                    .constrainAs(lazyColumnConstrainedReference) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, 8.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .pullRefresh(
                        state = pullRefreshState
                    )
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 16.dp
                    ),
                    contentPadding = PaddingValues(
                        bottom = 170.dp
                    ),
                    state = lazyColumnState
                ) {
                    // Calendar Item
                    item {
                        CalendarRowLayout(
                            modifier = Modifier
                                .animateItemPlacement(),
                            weatherNavigationScreen = WeatherNavigationScreen.Tomorrow,
                            weatherResponseModel = weatherResponseModel
                        )
                    }
                    // Current Temperature
                    item {
                        CurrentTemperatureRowLayout(
                            modifier = Modifier
                                .animateItemPlacement(),
                            currentWeatherDataModel = weatherResponseModel?.weatherForecastDataModel
                                ?.nextWeatherForecastDayDataModel?.currentWeatherDataModel
                                ?: return@item
                        )
                    }
                    // Day Hourly Temperature
                    item {
                        if (weatherResponseModel != null)
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
                            weatherHourlyDataModelList = weatherResponseModel?.weatherForecastDataModel?.nextWeatherForecastDayDataModel?.remainingWeatherHourlyDataModelList
                                ?: listOf()
                        )
                    }
                    // Sun & Moon
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
                            sunAndMoonStates = weatherResponseModel?.weatherForecastDataModel?.nextWeatherForecastDayDataModel?.weatherAstroDataModel?.availableSunAndMoonStates ?: listOf(),
                            weatherAstroDataModel = weatherResponseModel?.weatherForecastDataModel?.nextWeatherForecastDayDataModel?.weatherAstroDataModel
                        )
                    }
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(
                        Alignment.TopCenter
                    )
                )
            }

        FullScreenLottieLayout(
            state = requestLocationState,
            lottieAnimationResourceId = R.raw.location_animation,
            modifier = Modifier
                .constrainAs(progressIndicatorConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.value(
                        dp = 100.dp
                    )
                    height = Dimension.value(
                        dp = 100.dp
                    )
                }
        )

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

        FullScreenLottieLayout(
            state = networkErrorState,
            lottieAnimationResourceId = R.raw.no_internet_animation,
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
fun TomorrowWeatherLayoutPreview() {
    TomorrowWeatherLayout(
        viewModel = WeatherViewModel(),
        contentPadding = PaddingValues()
    )
}