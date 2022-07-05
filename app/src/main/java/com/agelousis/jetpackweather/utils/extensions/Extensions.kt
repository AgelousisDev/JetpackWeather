package com.agelousis.jetpackweather.utils.extensions

import com.agelousis.jetpackweather.utils.constants.Constants
import java.text.SimpleDateFormat
import java.util.*

fun Date.toDisplayDate(
    pattern: String = Constants.DISPLAY_DATE_FORMAT,
    plusDays: Int = 0
): String = with(SimpleDateFormat(pattern, Locale.getDefault())) {
    return if (plusDays > 0) {
        val calendar = Calendar.getInstance()
        calendar.time = this@toDisplayDate
        calendar.add(Calendar.DAY_OF_YEAR, plusDays)
        format(calendar.time)
    }
    else
        format(this@toDisplayDate)
}

fun String.toDate(
    pattern: String = Constants.DATE_FORMAT
): Date? = with(SimpleDateFormat(pattern, Locale.getDefault())) {
    parse(this@toDate)
}

inline fun <reified T : Enum<*>> valueEnumOrNull(name: String?): T? =
    T::class.java.enumConstants?.firstOrNull { it.name.lowercase() == name?.lowercase() }