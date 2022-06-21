package com.agelousis.jetpackweather.weather.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    Scaffold(
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

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherActivityLayoutPreview() {
    WeatherActivityBottomNavigationLayout()
}