package com.agelousis.jetpackweather.weather.ui

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.ui.composableView.WeatherBottomNavigation
import com.agelousis.jetpackweather.ui.composableView.WeatherTopAppBar
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

private val bottomNavigationItems by lazy {
    listOf(
        WeatherNavigationScreen.Today,
        WeatherNavigationScreen.Tomorrow,
        WeatherNavigationScreen.NextDays
    )
}

@Composable
fun WeatherActivityBottomNavigationLayout() {
    val navController = rememberNavController()
    val viewModel: WeatherViewModel = viewModel()
    val onBack: () -> Unit = {
        navController.navigateUp()
    }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec = decayAnimationSpec,
        canScroll = {
            true
        }
    )
    Scaffold(
        modifier = Modifier.nestedScroll(
            connection = scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            WeatherTopAppBar(
                modifier = Modifier
                    .statusBarsPadding(),
                title = stringResource(id = R.string.app_name),
                elevation = 0.dp,
                navigationIconBlock = onBack
            )
        },
        bottomBar = {
            WeatherBottomNavigation(
                navController = navController,
                items = bottomNavigationItems
            )
        }
    ) {
        WeatherActivityLayout(
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun WeatherActivityLayout(
    viewModel: WeatherViewModel,
    navController: NavController
) {
    val weatherResponseModel by viewModel.weatherResponseLiveData.observeAsState()
    val loaderState by viewModel.loaderStateStateFlow.collectAsState()
    ConstraintLayout {
        val progressIndicatorConstrainedReference = createRef()
        if (loaderState)
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressIndicatorConstrainedReference) {
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
fun WeatherActivityLayoutPreview() {
    WeatherActivityBottomNavigationLayout()
}