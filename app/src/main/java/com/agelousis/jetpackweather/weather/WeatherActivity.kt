package com.agelousis.jetpackweather.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agelousis.jetpackweather.ui.theme.JetpackWeatherTheme
import com.agelousis.jetpackweather.utils.extensions.weatherDrawerNavigationType
import com.agelousis.jetpackweather.weather.ui.WeatherActivityBottomNavigationLayout
import com.agelousis.jetpackweather.weather.viewModel.WeatherViewModel

class WeatherActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            //window?.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
            JetpackWeatherTheme {
                // A surface container using the 'background' color from the theme
                val windowSize = calculateWindowSizeClass(
                    activity = this
                )
                val viewModel: WeatherViewModel = viewModel()
                WeatherActivityBottomNavigationLayout(
                    viewModel = viewModel,
                    weatherDrawerNavigationType = windowSize.weatherDrawerNavigationType
                )
            }
        }
    }
}