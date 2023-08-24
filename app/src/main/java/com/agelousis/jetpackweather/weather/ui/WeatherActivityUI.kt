package com.agelousis.jetpackweather.weather.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.mapAddressPicker.MapAddressPickerActivity
import com.agelousis.jetpackweather.network.repositories.SuccessUnitBlock
import com.agelousis.jetpackweather.ui.composableView.*
import com.agelousis.jetpackweather.ui.composableView.models.PositiveButtonBlock
import com.agelousis.jetpackweather.ui.composableView.models.SimpleDialogDataModel
import com.agelousis.jetpackweather.ui.enumerations.WeatherDrawerNavigationType
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.ui.theme.slideVertically
import com.agelousis.jetpackweather.utils.extensions.arePermissionsGranted
import com.agelousis.jetpackweather.utils.extensions.getParcelable
import com.agelousis.jetpackweather.utils.helpers.LocationHelper
import com.agelousis.jetpackweather.utils.helpers.PreferencesStoreHelper
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.agelousis.jetpackweather.weather.drawerNavigation.WeatherDrawerNavigationScreen
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val weatherDrawerNavigationScreens = listOf(
    WeatherDrawerNavigationScreen.HomeWeather,
    WeatherDrawerNavigationScreen.Settings
)

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnsafeOptInUsageError")
@Composable
fun WeatherActivityBottomNavigationLayout(
    viewModel: WeatherViewModel,
    weatherDrawerNavigationType: WeatherDrawerNavigationType
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val showDialogState by viewModel.showDialogStateFlow.collectAsState()
    val isRefreshing by viewModel.swipeRefreshStateFlow.collectAsState()
    var requestLocationOnStartupState by remember {
        mutableStateOf(value = true)
    }
    val addressDataModel by viewModel.addressDataModelStateFlow.collectAsState()
    val weatherResponseModel by viewModel.weatherResponseLiveData.observeAsState()
    val lazyColumnFirstItemVisibilityState by viewModel.lazyColumnFirstChildVisibilityStateFlow.collectAsState()
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
        scope = scope,
        navController = navController,
        state = requestLocationOnStartupState,
        viewModel = viewModel
    ) {
        requestLocationOnStartupState = false
    }
    if (isRefreshing)
        requestWeather(
            context = context,
            navController = navController,
            viewModel = viewModel,
            longitude = viewModel.addressDataModelStateFlow.value?.longitude ?: 0.0,
            latitude = viewModel.addressDataModelStateFlow.value?.latitude ?: 0.0
        )
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    WeatherDrawerNavigation(
        modifier = Modifier
            .statusBarsPadding(),
        weatherDrawerNavigationType = weatherDrawerNavigationType,
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
        },
        footerContent = {
            TrademarkLayout(
                modifier = Modifier
                    .navigationBarsPadding()
                    .size(
                        width = 70.dp,
                        height = 50.dp
                    )
                    .align(
                        alignment = Alignment.End
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
                    navigationIcon = if (weatherDrawerNavigationType == WeatherDrawerNavigationType.NORMAL) Icons.Filled.Menu else null,
                    navigationIconBlock = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    actions = {
                        WeatherAppBarActions(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                )
            },
            bottomBar = {
                        AnimatedContent(
                            targetState = viewModel.currentNavigationRoute != WeatherDrawerNavigationScreen.Settings.route
                                    && if (weatherDrawerNavigationType == WeatherDrawerNavigationType.PERMANENT_NAVIGATION_DRAWER)
                                lazyColumnFirstItemVisibilityState
                            else
                                true,
                            transitionSpec = {
                                slideVertically
                            }
                        ) { state ->
                            if (state)
                                WeatherBottomNavigation(
                                    navController = navController,
                                    items = viewModel.bottomNavigationItems.also { bottomNavigationItems ->
                                        bottomNavigationItems.firstOrNull { weatherNavigationScreen ->
                                            weatherNavigationScreen is WeatherNavigationScreen.Alerts
                                        }?.badge =
                                            if (!weatherResponseModel?.weatherAlertsDataModel?.weatherAlertsModelList.isNullOrEmpty())
                                                weatherResponseModel?.weatherAlertsDataModel?.weatherAlertsModelList?.size?.toString()
                                            else
                                                null
                                    }
                                )
                        }
                /*Crossfade(
                    targetState = viewModel.currentNavigationRoute != WeatherDrawerNavigationScreen.Settings.route
                            && if (weatherDrawerNavigationType == WeatherDrawerNavigationType.PERMANENT_NAVIGATION_DRAWER)
                                    lazyColumnFirstItemVisibilityState
                               else
                                   true
                ) {
                    if (it)
                        WeatherBottomNavigation(
                            navController = navController,
                            items = viewModel.bottomNavigationItems.also { bottomNavigationItems ->
                                bottomNavigationItems.firstOrNull { weatherNavigationScreen ->
                                    weatherNavigationScreen is WeatherNavigationScreen.Alerts
                                }?.badge =
                                    if (!weatherResponseModel?.weatherAlertsDataModel?.weatherAlertsModelList.isNullOrEmpty())
                                        weatherResponseModel?.weatherAlertsDataModel?.weatherAlertsModelList?.size?.toString()
                                    else
                                        null
                            }
                        )
                }*/
            },
            content = { innerPadding ->
                if (viewModel.locationPermissionState
                    || context.arePermissionsGranted(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    || addressDataModel != null
                )
                    WeatherActivityNavigation(
                        viewModel = viewModel,
                        navController = navController,
                        contentPadding = innerPadding,
                        weatherDrawerNavigationType = weatherDrawerNavigationType
                    )
                else
                    LocationPermissionRequest(
                        navController = navController,
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
    contentPadding: PaddingValues,
    weatherDrawerNavigationType: WeatherDrawerNavigationType
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
                contentPadding = contentPadding,
                weatherDrawerNavigationType = weatherDrawerNavigationType
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
            route = WeatherNavigationScreen.Alerts.route
        ) {
            WeatherAlertsLayout(
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
                //viewModel.weatherResponseLiveData.value?.weatherLocationDataModel?.regionCountry
                    viewModel.addressDataModelStateFlow.value?.addressLine ?: innerNavController.context.resources.getString(R.string.app_name)
        }
    }
}

@Composable
fun WeatherAppBarActions(
    navController: NavController,
    viewModel: WeatherViewModel
) {
    val context = LocalContext.current
    val preferencesStoreHelper = PreferencesStoreHelper(
        context = context
    )
    val scope = rememberCoroutineScope()
    val mapAddressPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            viewModel.addressDataModelMutableStateFlow.value = activityResult.data?.getParcelable(MapAddressPickerActivity.CURRENT_ADDRESS)
            scope.launch {
                preferencesStoreHelper setCurrentAddressData viewModel.addressDataModelStateFlow.value
            }
            viewModel.weatherUiAppBarTitle = viewModel.addressDataModelStateFlow.value?.addressLine
            requestWeather(
                context = context,
                navController = navController,
                viewModel = viewModel,
                longitude = viewModel.addressDataModelStateFlow.value?.longitude
                    ?: return@rememberLauncherForActivityResult,
                latitude = viewModel.addressDataModelStateFlow.value?.latitude
                    ?: return@rememberLauncherForActivityResult
            )
        }
    }
    Crossfade(
        targetState = viewModel.currentNavigationRoute,
        label = "CurrentNavigationRoute"
    ) {
        if (it != WeatherDrawerNavigationScreen.Settings.route)
            // Current Location
            IconButton(
                enabled = viewModel.locationPermissionState
                        || context.arePermissionsGranted(
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                onClick = {
                    requestLocation(
                        context = context,
                        scope = scope,
                        navController = navController,
                        viewModel = viewModel,
                        fromUpdate = true
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
        targetState = viewModel.currentNavigationRoute,
        label = "CurrentNavigationRoute"
    ) {
        if (it != WeatherDrawerNavigationScreen.Settings.route)
            // Edit
            IconButton(
                onClick = {
                    mapAddressPickerLauncher.launch(
                        Intent(
                            context,
                            MapAddressPickerActivity::class.java
                        ).also { intent ->
                            intent.putExtra(
                                MapAddressPickerActivity.CURRENT_ADDRESS,
                                viewModel.addressDataModelStateFlow.value
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

@Composable
private fun LocationPermissionRequest(
    navController: NavController,
    viewModel: WeatherViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var locationPermissionDialogState by remember {
        mutableStateOf(value = false)
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { locationPermissions ->
        viewModel.locationPermissionState = locationPermissions.all {
            it.value
        }
        locationPermissionDialogState = locationPermissions.none {
            it.value
        }
        requestLocation(
            context = context,
            scope = scope,
            navController = navController,
            viewModel = viewModel
        )
    }

    LocationPermissionDialog(
        state = locationPermissionDialogState,
        positiveButtonBlock = {
            locationPermissionDialogState = false
            permissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        },
        dismissBlock = {
            locationPermissionDialogState = false
        }
    )

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

@Composable
private fun LocationPermissionDialog(
    state: Boolean,
    positiveButtonBlock: PositiveButtonBlock,
    dismissBlock: PositiveButtonBlock
) {
    SimpleDialog(
        show = state,
        simpleDialogDataModel = SimpleDialogDataModel(
            title = stringResource(id = R.string.key_warning_label),
            message = stringResource(id = R.string.key_location_permission_approval_message),
            positiveButtonBlock = positiveButtonBlock,
            dismissBlock = dismissBlock
        )
    )
}

private fun requestLocation(
    context: Context,
    scope: CoroutineScope,
    navController: NavController,
    state: Boolean = true,
    fromUpdate: Boolean = false,
    viewModel: WeatherViewModel,
    successUnitBlock: SuccessUnitBlock = {}
) {
    val preferencesStoreHelper = PreferencesStoreHelper(
        context = context
    )
    if (context.arePermissionsGranted(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        && state
    ) {
        if (!fromUpdate)
            viewModel.requestLocationMutableState.value = true
        LocationHelper(
            context = context,
            priority = Priority.PRIORITY_HIGH_ACCURACY
        ) { location ->
            if (!fromUpdate)
                viewModel.requestLocationMutableState.value = false
            LocationHelper.getAddressFromLocation(
                context = context,
                longitude = location.longitude,
                latitude = location.latitude
            ) { addressDataModel ->
                viewModel.addressDataModelMutableStateFlow.value = addressDataModel

                viewModel.weatherUiAppBarTitle = addressDataModel?.addressLine
                scope.launch {
                    preferencesStoreHelper setCurrentAddressData addressDataModel
                }
                requestWeather(
                    context = context,
                    navController = navController,
                    viewModel = viewModel,
                    longitude = location.longitude,
                    latitude = location.latitude
                )
                successUnitBlock()
            }
        }
    }
}

fun requestWeather(
    context: Context,
    navController: NavController,
    viewModel: WeatherViewModel,
    longitude: Double,
    latitude: Double
) {
    viewModel.requestForecast(
        context = context,
        navController = navController,
        location = "%s,%s".format(
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
        viewModel = viewModel<WeatherViewModel>().also { weatherViewModel ->
            weatherViewModel.bottomNavigationItems.add(
                WeatherNavigationScreen.Alerts
            )
        },
        weatherDrawerNavigationType = WeatherDrawerNavigationType.PERMANENT_NAVIGATION_DRAWER
    )
}