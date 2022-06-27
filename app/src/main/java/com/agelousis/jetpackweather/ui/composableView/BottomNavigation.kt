package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                shadowElevation = 2.2f
            }
            .height(
                height = 95.dp
            )
    ) {
        items.forEach { weatherNavigationScreen ->
            NavigationBarItem(
                modifier = Modifier
                    .navigationBarsPadding(),
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