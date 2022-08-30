package com.agelousis.jetpackweather.network.response

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
)