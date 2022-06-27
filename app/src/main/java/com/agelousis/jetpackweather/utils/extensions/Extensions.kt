package com.agelousis.jetpackweather.utils.extensions

import com.agelousis.jetpackweather.utils.constants.Constants
import java.text.SimpleDateFormat
import java.util.*

fun Date.toDisplayDate(
    pattern: String = Constants.DISPLAY_DATE_FORMAT
) = with(SimpleDateFormat(pattern, Locale.getDefault())) {
    format(this@toDisplayDate)
}