package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    NavigationBar(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(
                    size = 30.dp
                )
            }
    ) {
        items.forEach { weatherNavigationScreen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            id = weatherNavigationScreen.icon
                        ),
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