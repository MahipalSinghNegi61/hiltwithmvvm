package com.tech.hilt_mvvm.data.network

import com.tech.hilt_mvvm.data.network.response.CurrentWeatherResponse
import com.tech.hilt_mvvm.data.network.response.ProductResponse
import com.tech.hilt_mvvm.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(Constants.FAKE_API_BASE_URL)
    suspend fun fetchProductList(): Response<List<ProductResponse>>


    @GET(Constants.WEATHER_BASE_URL)
    suspend fun fetchCurrentWeather(
        @Query("key") key: String,
        @Query("q") city: String,
        @Query("aqi") aqi: String
    ): Response<CurrentWeatherResponse>
}