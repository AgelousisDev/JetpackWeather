package com.agelousis.jetpackweather.network.repositories

import com.agelousis.jetpackweather.network.NetworkHelper
import com.agelousis.jetpackweather.network.apis.WeatherAPI
import com.agelousis.jetpackweather.network.response.ErrorModel
import com.agelousis.jetpackweather.network.response.WeatherResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

typealias SuccessBlock<T> = (T) -> Unit
typealias FailureBlock = (ErrorModel) -> Unit

object WeatherRepository {

    /**
     * @param location
     * @param airQualityState
     */
    fun requestCurrentWeather(
        location: String,
        airQualityState: Boolean,
        successBlock: SuccessBlock<WeatherResponseModel>,
        failureBlock: FailureBlock
    ) {
        NetworkHelper.create<WeatherAPI>()?.requestCurrentWeather(
            location = location,
            airQualityState = if (airQualityState) "yes" else "no"
        )?.enqueue(
            object: Callback<WeatherResponseModel> {
                override fun onResponse(
                    call: Call<WeatherResponseModel>,
                    response: Response<WeatherResponseModel>
                ) {
                    successBlock(response.body() ?: return)
                }

                override fun onFailure(call: Call<WeatherResponseModel>, t: Throwable) {
                    failureBlock(
                        ErrorModel(
                            localizedMessage = t.localizedMessage
                        )
                    )
                }
            }
        )
    }

}