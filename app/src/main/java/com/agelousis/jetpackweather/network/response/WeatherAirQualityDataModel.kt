package com.agelousis.jetpackweather.network.response

import com.google.gson.annotations.SerializedName

data class WeatherAirQualityDataModel(
    @SerializedName(value = "co") val co: Double?,
    @SerializedName(value = "no2") val no2: Double?,
    @SerializedName(value = "o3") val o3: Double?,
    @SerializedName(value = "so2") val so2: Double?,
    @SerializedName(value = "pm2_5") val pm25: Double?,
    @SerializedName(value = "pm10") val pm10: Double?,
    @SerializedName(value = "us-epa-index") val usEpaIndex: Double?,
    @SerializedName(value = "gb-defra-index") val gbDefraIndex: Double?
)

/**
 * Air Quality Data
Air Quality data is returned in the Forecast API and Realtime API response. Depending upon your subscription plan we provide current and 3 day air quality data for the given location in json and xml.

It provides air quality index (see below) data on major pollutant gases like Carbon monoxide (CO), Ozone (O3), Nitrogen dioxide (NO2), Sulphur dioxide (SO2), PM 2.5 and PM 10.

By default air quality data is not returned. To get air quality data back in the response from Forecast API and Realtime API, pass the parameter aqi=yes.

Field	Data Type	Description
co	float	Carbon Monoxide (μg/m3)
o3	float	Ozone (μg/m3)
no2	float	Nitrogen dioxide (μg/m3)
so2	float	Sulphur dioxide (μg/m3)
pm2_5	float	PM2.5 (μg/m3)
pm10	float	PM10 (μg/m3)
us-epa-index	integer	US - EPA standard.
1 means Good
2 means Moderate
3 means Unhealthy for sensitive group
4 means Unhealthy
5 means Very Unhealthy
6 means Hazardous
gb-defra-index	integer	UK Defra Index (See table below)
UK DEFRA INDEX Table
Index	1	2	3	4	5	6	7	8	9	10
Band	Low	Low	Low	Moderate	Moderate	Moderate	High	High	High	Very High
µgm-3	0-11	12-23	24-35	36-41	42-47	48-53	54-58	59-64	65-70	71 or more
 */