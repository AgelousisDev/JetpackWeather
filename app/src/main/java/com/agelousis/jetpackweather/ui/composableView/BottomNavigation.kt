package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.weather.bottomNavigation.WeatherNavigationScreen

@Composable
fun WeatherBottomNavigation(
    navController: NavHostController,
    items: List<WeatherNavigationScreen>
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    NavigationBar {
        items.forEachIndexed { index, weatherNavigationScreen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = weatherNavigationScreen.resourceId),
                        style = Typography.bodyMedium
                    )
                },
                selected = currentRoute == weatherNavigationScreen.route,
                onClick = {
                    navController.navigate(weatherNavigationScreen.route) {
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