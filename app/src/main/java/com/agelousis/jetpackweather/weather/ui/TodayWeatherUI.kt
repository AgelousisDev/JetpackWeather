package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.ui.composableView.SimpleDialog
import com.agelousis.jetpackweather.ui.composableView.models.SimpleDialogDataModel
import com.agelousis.jetpackweather.weather.rows.CalendarRowLayout
import com.agelousis.jetpackweather.weather.rows.CurrentTemperatureRowLayout
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

@Composable
fun TodayWeatherLayout(
    viewModel: WeatherViewModel,
    contentPadding: PaddingValues
) {
    val showDialogState by viewModel.showDialog.collectAsState()
    val loaderState by viewModel.loaderStateStateFlow.collectAsState()
    val weatherResponseModel by viewModel.weatherResponseLiveData.observeAsState()
    SimpleDialog(
        show = showDialogState,
        simpleDialogDataModel = SimpleDialogDataModel(
            title = viewModel.alertPair.first ?: "",
            message = viewModel.alertPair.second ?: ""
        )
    )
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
        val (lazyColumnConstrainedReference, progressIndicatorConstrainedReference) = createRefs()
        LazyColumn(
            modifier = Modifier
                .constrainAs(lazyColumnConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, 32.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(
                space = 32.dp
            )
        ) {
            item {
                CalendarRowLayout(
                    weatherResponseModel = weatherResponseModel
                )
            }
            item {
                CurrentTemperatureRowLayout(
                    weatherResponseModel = weatherResponseModel
                )
            }
        }
        if (loaderState)
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(progressIndicatorConstrainedReference) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
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