package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import coil.compose.rememberAsyncImagePainter
import com.agelousis.jetpackweather.ui.composableView.SimpleDialog
import com.agelousis.jetpackweather.ui.composableView.models.SimpleDialogDataModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.textViewHeaderFont
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

@Composable
fun TodayWeatherLayout(
    viewModel: WeatherViewModel,
    contentPadding: PaddingValues
) {
    val showDialogState by viewModel.showDialog.collectAsState()
    val loaderState by viewModel.loaderStateStateFlow.collectAsState()
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
                }
        ) {
            item {
                CurrentTemperatureRowLayout(
                    viewModel = viewModel
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

@Composable
private fun CurrentTemperatureRowLayout(
    viewModel: WeatherViewModel
) {
    val weatherResponseModel by viewModel.weatherResponseLiveData.observeAsState()
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
                model = weatherResponseModel?.currentWeatherDataModel?.condition?.iconUrl
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
            text = weatherResponseModel?.currentWeatherDataModel?.condition?.text ?: "",
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TodayWeatherLayoutPreview() {
    TodayWeatherLayout(
        viewModel = WeatherViewModel(),
        contentPadding = PaddingValues()
    )
}