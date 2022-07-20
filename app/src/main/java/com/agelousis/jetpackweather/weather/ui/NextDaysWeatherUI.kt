package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.network.response.CurrentDayWeatherDataModel
import com.agelousis.jetpackweather.network.response.WeatherHourlyDataModel
import com.agelousis.jetpackweather.ui.models.HeaderModel
import com.agelousis.jetpackweather.ui.rows.HeaderRowLayout
import com.agelousis.jetpackweather.weather.rows.ForecastDayWeatherLayout
import com.agelousis.jetpackweather.weather.rows.HourlyWeatherConditionsRowLayout
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NextDaysWeatherLayout(
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
                    items(
                        items =
                            if (weatherResponseModel != null)
                                viewModel.nextDaysForecastDataList
                            else
                                listOf()
                    ) { forecastItem ->
                        when(forecastItem) {
                            is HeaderModel ->
                                HeaderRowLayout(
                                    modifier = Modifier
                                        .animateItemPlacement(),
                                    headerModel = forecastItem
                                )
                            is CurrentDayWeatherDataModel ->
                                ForecastDayWeatherLayout(
                                    currentDayWeatherDataModel = forecastItem
                                )
                            is List<*> ->
                                HourlyWeatherConditionsRowLayout(
                                    modifier = Modifier
                                        .animateItemPlacement(),
                                    weatherHourlyDataModelList = forecastItem.filterIsInstance<WeatherHourlyDataModel>()
                                )
                        }
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
fun NextDaysWeatherLayoutPreview() {
    NextDaysWeatherLayout(
        viewModel = WeatherViewModel(),
        contentPadding = PaddingValues()
    )
}