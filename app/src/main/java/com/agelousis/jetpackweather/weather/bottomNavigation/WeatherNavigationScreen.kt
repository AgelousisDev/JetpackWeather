package com.agelousis.jetpackweather.weather.bottomNavigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.ui.graphics.vector.ImageVector
import com.agelousis.jetpackweather.R

sealed class WeatherNavigationScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Today: WeatherNavigationScreen(
        route = "todayRoute",
        resourceId = R.string.key_today_label,
        icon = Icons.Filled.AccountBox
    )
    object Tomorrow: WeatherNavigationScreen(
        route = "tomorrowRoute",
        resourceId = R.string.key_tomorrow_label,
        icon = Icons.Filled.AccountBox
    )
    object NextDays: WeatherNavigationScreen(
        route = "nextDaysRoute",
        resourceId = R.string.key_next_days,
        icon = Icons.Filled.AccountBox
    )
}