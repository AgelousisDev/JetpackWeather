package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen
import com.agelousis.jetpackweather.weather.drawerNavigation.WeatherDrawerNavigationScreen
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

@Composable
fun WeatherNavigationRail(
    modifier: Modifier = Modifier,
    navController: NavController,
    weatherDrawerNavigationScreens: List<WeatherDrawerNavigationScreen>,
    viewModel: WeatherViewModel,
    headerContent: @Composable ColumnScope.() -> Unit = {}
) {
    var selectedItem by remember {
        mutableStateOf(value = weatherDrawerNavigationScreens[0])
    }
    selectedItem = WeatherNavigationScreen.fromRoute(
        route = viewModel.currentNavigationRoute
    )?.weatherDrawerNavigationScreen
        ?: (WeatherDrawerNavigationScreen fromRoute viewModel.currentNavigationRoute)
                ?: weatherDrawerNavigationScreens[0]
    NavigationRail(
        modifier = modifier,
        header = headerContent
    ) {
        weatherDrawerNavigationScreens.forEach { weatherDrawerNavigationScreen ->
            NavigationRailItem(
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
                    selectedItem = weatherDrawerNavigationScreen
                    navController.navigate(weatherDrawerNavigationScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}