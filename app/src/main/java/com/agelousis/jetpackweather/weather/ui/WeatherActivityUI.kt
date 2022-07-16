package com.agelousis.jetpackweather.weather.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.mapAddressPicker.MapAddressPickerActivity
import com.agelousis.jetpackweather.network.repositories.SuccessUnitBlock
import com.agelousis.jetpackweather.ui.composableView.SimpleDialog
import com.agelousis.jetpackweather.ui.composableView.WeatherBottomNavigation
import com.agelousis.jetpackweather.ui.composableView.WeatherDrawerNavigation
import com.agelousis.jetpackweather.ui.composableView.WeatherSmallTopAppBar
import com.agelousis.jetpackweather.ui.composableView.models.SimpleDialogDataModel
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.utils.extensions.arePermissionsGranted
import com.agelousis.jetpackweather.utils.helpers.LocationHelper
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.agelousis.jetpackweather.weather.drawerNavigation.WeatherDrawerNavigationScreen
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
private val weatherDrawerNavigationScreens = listOf(
    WeatherDrawerNavigationScreen.HomeWeather,
    WeatherDrawerNavigationScreen.Settings
)

@Composable
fun WeatherActivityBottomNavigationLayout(
    viewModel: WeatherViewModel
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val showDialogState by viewModel.showDialog.collectAsState()
    val isRefreshing by viewModel.swipeRefreshStateFlow.collectAsState()
    var requestLocationOnStartupState by remember {
        mutableStateOf(value = true)
    }
    val onBack: () -> Unit = {
        navController.navigateUp()
    }
    BackHandler(
        onBack = onBack
    )
    SimpleDialog(
        show = showDialogState,
        simpleDialogDataModel = SimpleDialogDataModel(
            title = viewModel.alertPair.first ?: "",
            message = viewModel.alertPair.second ?: "",
            positiveButtonBlock = {
                viewModel.onDialogConfirm()
            },
            dismissBlock = {
                viewModel.onDialogDismiss()
            }
        )
    )
    /*val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec = decayAnimationSpec,
        state = rememberTopAppBarScrollState()
    )*/
    requestLocation(
        context = context,
        state = requestLocationOnStartupState,
        viewModel = viewModel
    ) {
        requestLocationOnStartupState = false
    }
    if (isRefreshing)
        requestWeather(
            viewModel = viewModel,
            longitude = viewModel.addressDataModelStateFlow.value?.longitude ?: 0.0,
            latitude = viewModel.addressDataModelStateFlow.value?.latitude ?: 0.0
        )
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    WeatherDrawerNavigation(
        modifier = Modifier
            .statusBarsPadding(),
        viewModel  = viewModel,
        drawerState = drawerState,
        coroutineScope = scope,
        navController = navController,
        weatherDrawerNavigationScreens = weatherDrawerNavigationScreens,
        headerContent = {
            Image(
                painter = painterResource(id = R.drawable.app_icon_foreground),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        top = 32.dp
                    )
                    .align(
                        alignment = Alignment.CenterHorizontally
                    )
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = Typography.displayLarge,
                modifier = Modifier
                    .align(
                        alignment = Alignment.CenterHorizontally
                    )
            )
        }
    ) {
        Scaffold(
            /*modifier = Modifier.nestedScroll(
                connection = scrollBehavior.nestedScrollConnection
            ),*/
            topBar = {
                WeatherSmallTopAppBar(
                    title = viewModel.weatherUiAppBarTitle ?: stringResource(
                        id = R.string.app_name
                    ),
                    //scrolledContainerColor = MaterialTheme.colorScheme.surface,
                    //scrollBehavior = scrollBehavior,
                    navigationIcon = Icons.Filled.Menu,
                    navigationIconBlock = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    actions = {
                        WeatherAppBarActions(
                            viewModel = viewModel,
                            rowScope = this
                        )
                    }
                )
            },
            bottomBar = {
                Crossfade(
                    targetState = viewModel.currentNavigationRoute
                ) {
                    if (it != WeatherDrawerNavigationScreen.Settings.route)
                        WeatherBottomNavigation(
                            navController = navController,
                            items = bottomNavigationItems
                        )
                }
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
        composable(
            route = WeatherDrawerNavigationScreen.Settings.route
        ) {
            SettingsLayout(
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        }
    }
    navController.addOnDestinationChangedListener { innerNavController, destination, _ ->
        viewModel.currentNavigationRoute = destination.route ?: WeatherNavigationScreen.Today.route
        viewModel.weatherUiAppBarTitle = when(destination.route) {
            WeatherDrawerNavigationScreen.Settings.route ->
                innerNavController.context.resources.getString(R.string.key_settings_label)
            else ->
                viewModel.weatherResponseLiveData.value?.weatherLocationDataModel?.regionCountry
                    ?: innerNavController.context.resources.getString(R.string.app_name)
        }
    }
}

@Composable
fun WeatherAppBarActions(
    rowScope: RowScope,
    viewModel: WeatherViewModel
) {
    val context = LocalContext.current
    val addressDataModel by viewModel.addressDataModelStateFlow.collectAsState()
    val mapAddressPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            viewModel.addressDataModelMutableStateFlow.value =
                activityResult.data?.getParcelableExtra(MapAddressPickerActivity.CURRENT_ADDRESS)
            requestWeather(
                viewModel = viewModel,
                longitude = viewModel.addressDataModelStateFlow.value?.longitude ?: return@rememberLauncherForActivityResult,
                latitude = viewModel.addressDataModelStateFlow.value?.latitude ?: return@rememberLauncherForActivityResult
            )
        }
    }
    rowScope.apply {
        Crossfade(
            targetState = viewModel.currentNavigationRoute
        ) {
            if (it != WeatherDrawerNavigationScreen.Settings.route)
            // Current Location
                IconButton(
                    onClick = {
                        requestLocation(
                            context = context,
                            viewModel = viewModel
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null
                    )
                }
        }
        Crossfade(
            targetState = viewModel.currentNavigationRoute
        ) {
            if (it != WeatherDrawerNavigationScreen.Settings.route)
            // Edit
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
    state: Boolean = true,
    viewModel: WeatherViewModel,
    successUnitBlock: SuccessUnitBlock = {}
) {
    if (context.arePermissionsGranted(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        && state
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
                viewModel = viewModel,
                longitude = it.longitude,
                latitude = it.latitude
            )
            successUnitBlock()
        }
}

fun requestWeather(
    viewModel: WeatherViewModel,
    longitude: Double,
    latitude: Double
) {
    viewModel.requestForecast(
        location = "%f,%f".format(
            latitude,
            longitude
        ),
        days = 7,
        airQualityState = true,
        alertsState = true
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherActivityLayoutPreview() {
    WeatherActivityBottomNavigationLayout(
        viewModel = WeatherViewModel()
    )
}