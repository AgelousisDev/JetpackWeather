package com.agelousis.jetpackweather.network.apis

import com.agelousis.jetpackweather.BuildConfig
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET(value = "current.json")
    fun requestCurrentWeather(
        @Query(value = "key") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query(value = "q") location: String,
        @Query(value = "aqi") airQualityState: String = "no"
    ): Call<WeatherResponseModel>

}