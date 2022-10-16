package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.agelousis.jetpackweather.ui.enumerations.WeatherDrawerNavigationType
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.agelousis.jetpackweather.weather.drawerNavigation.WeatherDrawerNavigationScreen
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun WeatherDrawerNavigation(
    modifier: Modifier = Modifier,
    weatherDrawerNavigationType: WeatherDrawerNavigationType,
    viewModel: WeatherViewModel,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavController,
    weatherDrawerNavigationScreens: List<WeatherDrawerNavigationScreen>,
    headerContent: @Composable ColumnScope.() -> Unit = {},
    footerContent: @Composable ColumnScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    var selectedItem by remember {
        mutableStateOf(value = weatherDrawerNavigationScreens[0])
    }
    selectedItem = WeatherNavigationScreen.fromRoute(
        route = viewModel.currentNavigationRoute
    )?.weatherDrawerNavigationScreen
        ?: (WeatherDrawerNavigationScreen fromRoute viewModel.currentNavigationRoute)
                ?: weatherDrawerNavigationScreens[0]
    when(weatherDrawerNavigationType) {
        WeatherDrawerNavigationType.PERMANENT_NAVIGATION_DRAWER ->
            PermanentNavigationDrawer(
                modifier = modifier,
                drawerContent = {
                    ModalDrawerSheet(
                        drawerShape = RoundedCornerShape(
                            topEnd = 32.dp,
                            bottomEnd = 32.dp
                        ),
                        modifier = Modifier

                            .background(
                                color = Color.Transparent
                            )
                    ) {
                        headerContent()
                        Spacer(
                            modifier = Modifier
                                .padding(
                                    top = 32.dp
                                )
                        )
                        weatherDrawerNavigationScreens.forEach { weatherDrawerNavigationScreen ->
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        imageVector = weatherDrawerNavigationScreen.icon,
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = weatherDrawerNavigationScreen.label),
                                        style = Typography.bodyMedium
                                    )
                                },
                                selected = weatherDrawerNavigationScreen == selectedItem,
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                    selectedItem = weatherDrawerNavigationScreen
                                    navController.navigate(weatherDrawerNavigationScreen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                modifier = Modifier
                                    .padding(
                                        paddingValues = NavigationDrawerItemDefaults.ItemPadding
                                    )
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .weight(
                                    weight = 1f
                                )
                        )
                        footerContent()
                    }
                },
                content = content
            )
        else ->
            ModalNavigationDrawer(
                modifier = modifier,
                scrimColor = Color.Transparent,
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        drawerShape = RoundedCornerShape(
                            topEnd = 32.dp,
                            bottomEnd = 32.dp
                        )
                    ) {
                        headerContent()
                        Spacer(
                            modifier = Modifier
                                .padding(
                                    top = 32.dp
                                )
                        )
                        weatherDrawerNavigationScreens.forEach { weatherDrawerNavigationScreen ->
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        imageVector = weatherDrawerNavigationScreen.icon,
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = weatherDrawerNavigationScreen.label),
                                        style = Typography.bodyMedium
                                    )
                                },
                                selected = weatherDrawerNavigationScreen == selectedItem,
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                    selectedItem = weatherDrawerNavigationScreen
                                    navController.navigate(weatherDrawerNavigationScreen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                modifier = Modifier
                                    .padding(
                                        paddingValues = NavigationDrawerItemDefaults.ItemPadding
                                    )
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .weight(
                                    weight = 1f
                                )
                        )
                        footerContent()
                    }
                },
                content = content
            )
    }
}