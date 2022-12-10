package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.ui.composableView.FullScreenLottieLayout
import com.agelousis.jetpackweather.ui.rows.WeatherAlertRowLayout
import com.agelousis.jetpackweather.ui.theme.weatherBackgroundGradient
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

@Composable
fun WeatherAlertsLayout(
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
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 170.dp
                    )
                ) {
                    items(
                        items = weatherResponseModel?.weatherAlertsDataModel
                            ?.weatherAlertsModelList
                            ?: listOf()
                    ) { weatherAlertModel ->
                        WeatherAlertRowLayout(
                            modifier = Modifier
                                .animateItemPlacement(),
                            weatherAlertModel = weatherAlertModel
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
            state = networkErrorState
                    || (weatherResponseModel != null
                    && weatherResponseModel?.weatherAlertsDataModel?.weatherAlertsModelList.isNullOrEmpty()),
            lottieAnimationResourceId = if (networkErrorState) R.raw.no_internet_animation else R.raw.happy_earth_animation,
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
                    if (networkErrorState)
                        viewModel.swipeRefreshMutableStateFlow.value = true
                }
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherAlertsLayoutPreview() {
    WeatherAlertsLayout(
        viewModel = viewModel(),
        contentPadding = PaddingValues()
    )
}