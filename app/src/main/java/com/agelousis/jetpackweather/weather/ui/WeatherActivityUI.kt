package com.agelousis.jetpackweather.weather.ui

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.mapAddressPicker.MapAddressPickerActivity
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
    val context = LocalContext.current
    val navController = rememberNavController()
    val viewModel: WeatherViewModel = viewModel()
    val onBack: () -> Unit = {
        navController.navigateUp()
    }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec = decayAnimationSpec
    )
    val mapAddressPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {

    }
    Scaffold(
        modifier = Modifier.nestedScroll(
            connection = scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            WeatherTopAppBar(
                title = stringResource(id = R.string.app_name),
                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                scrollBehavior = scrollBehavior,
                navigationIconBlock = onBack,
                actions = {
                    IconButton(
                        onClick = {
                            mapAddressPickerLauncher.launch(
                                Intent(
                                    context,
                                    MapAddressPickerActivity::class.java
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            WeatherActivityNavigation(
                viewModel = viewModel,
                navController = navController,
                contentPadding = innerPadding
            )
        },
        bottomBar = {
            WeatherBottomNavigation(
                navController = navController,
                items = bottomNavigationItems
            )
        }
    )
}

@Composable
fun WeatherActivityNavigation(
    viewModel: WeatherViewModel,
    navController: NavHostController,
    contentPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = WeatherNavigationScreen.Today.route
    ) {
        composable(
            route = WeatherNavigationScreen.Today.route
        ) {
            TodayWeatherLayout(
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        }
        composable(
            route = WeatherNavigationScreen.Tomorrow.route
        ) {
            TomorrowWeatherLayout(
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        }
        composable(
            route = WeatherNavigationScreen.NextDays.route
        ) {
            NextDaysWeatherLayout(
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherActivityLayoutPreview() {
    WeatherActivityBottomNavigationLayout()
}