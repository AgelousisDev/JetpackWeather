package com.agelousis.jetpackweather.weather.bottomNavigation

import androidx.annotation.StringRes
import com.agelousis.jetpackweather.R

sealed class WeatherNavigationScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: Int
) {
    object Today: WeatherNavigationScreen(
        route = "todayRoute",
        resourceId = R.string.key_today_label,
        icon = R.drawable.ic_today
    )
    object Tomorrow: WeatherNavigationScreen(
        route = "tomorrowRoute",
        resourceId = R.string.key_tomorrow_label,
        icon = R.drawable.ic_tomorrow
    )
    object NextDays: WeatherNavigationScreen(
        route = "nextDaysRoute",
        resourceId = R.string.key_next_days,
        icon = R.drawable.ic_next_days
    )
}