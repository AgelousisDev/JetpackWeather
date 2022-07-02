package com.agelousis.jetpackweather.ui.composableView

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.agelousis.jetpackweather.ui.theme.Typography
import com.agelousis.jetpackweather.weather.drawerNavigation.WeatherDrawerNavigationScreen
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun WeatherDrawerNavigation(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavController,
    weatherDrawerNavigationScreens: List<WeatherDrawerNavigationScreen>,
    headerContent: @Composable ColumnScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    var selectedItem by remember {
        mutableStateOf(value = weatherDrawerNavigationScreens[0])
    }
    ModalNavigationDrawer(
        modifier = modifier,
        scrimColor = Color.Transparent,
        drawerState = drawerState,
        drawerContent = {
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
                        viewModel.currentNavigationRoute = weatherDrawerNavigationScreen.route
                    },
                    modifier = Modifier
                        .padding(
                            paddingValues = NavigationDrawerItemDefaults.ItemPadding
                        )
                )
            }
        },
        content = content
    )
}