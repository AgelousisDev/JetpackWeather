package com.agelousis.jetpackweather.network.response

import android.content.Context
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.network.response.enumerations.WeatherAlertSeverity
import com.agelousis.jetpackweather.network.response.enumerations.WeatherAlertUrgency
import com.agelousis.jetpackweather.utils.constants.Constants
import com.agelousis.jetpackweather.utils.extensions.toDate
import com.agelousis.jetpackweather.utils.extensions.toDisplayDate
import com.google.gson.annotations.SerializedName

data class WeatherAlertModel(
    @SerializedName(value = "headline") val headline: String?,
    @SerializedName(value = "msgtype") val msgType: String?,
    @SerializedName(value = "severity") val severity: String?,
    @SerializedName(value = "urgency") val urgency: String?,
    @SerializedName(value = "areas") val areas: String?,
    @SerializedName(value = "category") val category: String?,
    @SerializedName(value = "event") val event: String?,
    @SerializedName(value = "note") val note: String?,
    @SerializedName(value = "effective") val effective: String?,
    @SerializedName(value = "expires") val expires: String?,
    @SerializedName(value = "desc") val desc: String?,
    @SerializedName(value = "instruction") val instruction: String?
) {

    infix fun alertExpirationLabelWith(context: Context) =
        context.resources.getString(
            R.string.key_from_date_to_date_with_dates_label,
            effective?.toDate(
                pattern = Constants.SERVER_FULL_DATE_TIME_FORMAT
            )?.toDisplayDate(),
            expires?.toDate(
                pattern = Constants.SERVER_FULL_DATE_TIME_FORMAT
            )?.toDisplayDate()
        )

    val severityType
        get() = WeatherAlertSeverity.values().firstOrNull { weatherAlertSeverity ->
            weatherAlertSeverity.type == severity
        }

    val urgencyType
        get() = WeatherAlertUrgency.values().firstOrNull { weatherAlertUrgency ->
            weatherAlertUrgency.type == urgency
        }

}