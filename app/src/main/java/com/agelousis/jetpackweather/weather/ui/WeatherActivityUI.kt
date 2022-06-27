package com.agelousis.jetpackweather.weather.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.mapAddressPicker.MapAddressPickerActivity
import com.agelousis.jetpackweather.ui.composableView.WeatherBottomNavigation
import com.agelousis.jetpackweather.ui.composableView.WeatherTopAppBar
import com.agelousis.jetpackweather.utils.extensions.arePermissionsGranted
import com.agelousis.jetpackweather.utils.helpers.LocationHelper
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val bottomNavigationItems by lazy {
    listOf(
        WeatherNavigationScreen.Today,
        WeatherNavigationScreen.Tomorrow,
        WeatherNavigationScreen.NextDays
    )
}

@Composable
fun WeatherActivityBottomNavigationLayout(
    viewModel: WeatherViewModel
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val onBack: () -> Unit = {
        navController.navigateUp()
    }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec = decayAnimationSpec
    )
    val addressDataModel by viewModel.addressDataModelStateFlow.collectAsState()
    val mapAddressPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK)
            viewModel.addressDataModelMutableStateFlow.value =
                activityResult.data?.getParcelableExtra(MapAddressPickerActivity.CURRENT_ADDRESS)
        requestWeather(
            context = context,
            viewModel = viewModel,
            longitude = viewModel.addressDataModelStateFlow.value?.longitude ?: return@rememberLauncherForActivityResult,
            latitude = viewModel.addressDataModelStateFlow.value?.latitude ?: return@rememberLauncherForActivityResult
        )
    }
    requestLocation(
        context = context,
        viewModel = viewModel
    )
    Scaffold(
        modifier = Modifier.nestedScroll(
            connection = scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            WeatherTopAppBar(
                title = addressDataModel?.addressLine ?: stringResource(
                    id = R.string.app_name
                ),
                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                scrollBehavior = scrollBehavior,
                navigationIconBlock = onBack,
                actions = {
                    IconButton(
                        enabled = addressDataModel != null,
                        onClick = {
                            mapAddressPickerLauncher.launch(
                                Intent(
                                    context,
                                    MapAddressPickerActivity::class.java
                                ).also { intent ->
                                    intent.putExtra(
                                        MapAddressPickerActivity.CURRENT_ADDRESS,
                                        addressDataModel
                                    )
                                }
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
        bottomBar = {
            WeatherBottomNavigation(
                navController = navController,
                items = bottomNavigationItems
            )
        },
        content = { innerPadding ->
            if (viewModel.locationPermissionState
                || context.arePermissionsGranted(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
                WeatherActivityNavigation(
                    viewModel = viewModel,
                    navController = navController,
                    contentPadding = innerPadding
                )
            else
                LocationPermissionRequest(
                    viewModel = viewModel
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

@Composable
private fun LocationPermissionRequest(
    viewModel: WeatherViewModel
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { locationPermissions ->
        viewModel.locationPermissionState = locationPermissions.all {
            it.value
        }
        requestLocation(
            context = context,
            viewModel = viewModel
        )
    }
    LaunchedEffect(
        key1 = Unit
    ) {
        launch {
            delay(
                timeMillis = 1000
            )
            permissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }
}

private fun requestLocation(
    context: Context,
    viewModel: WeatherViewModel,
) {
    if (context.arePermissionsGranted(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
        LocationHelper(
            context = context,
            priority = PRIORITY_HIGH_ACCURACY
        ) {
            viewModel.addressDataModelMutableStateFlow.value = viewModel.getAddressFromLocation(
                context = context,
                longitude = it.longitude,
                latitude = it.latitude
            )
            requestWeather(
                context = context,
                viewModel = viewModel,
                longitude = it.longitude,
                latitude = it.latitude
            )
        }
}

private fun requestWeather(
    context: Context,
    viewModel: WeatherViewModel,
    longitude: Double,
    latitude: Double
) {
    viewModel.requestCurrentWeather(
        context = context,
        location = "%f,%f".format(
            latitude,
            longitude
        ),
        airQualityState = true
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherActivityLayoutPreview() {
    WeatherActivityBottomNavigationLayout(
        viewModel = WeatherViewModel()
    )
}