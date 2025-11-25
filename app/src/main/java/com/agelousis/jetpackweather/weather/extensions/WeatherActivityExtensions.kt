package com.agelousis.jetpackweather.weather.extensions

import com.agelousis.jetpackweather.utils.enumerations.LanguageEnum
import com.agelousis.jetpackweather.utils.extensions.setAppLanguage
import com.agelousis.jetpackweather.weather.WeatherActivity

infix fun WeatherActivity.setAppLanguage(
    languageEnum: LanguageEnum
) {
    this setAppLanguage languageEnum.locale
}