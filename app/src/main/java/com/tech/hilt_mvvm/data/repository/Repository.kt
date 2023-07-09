package com.tech.hilt_mvvm.data.repository

import com.tech.hilt_mvvm.data.network.ApiInterface
import com.tech.hilt_mvvm.data.network.SafeApiRequest
import com.tech.hilt_mvvm.data.network.response.CurrentWeatherResponse
import com.tech.hilt_mvvm.data.network.response.ProductResponse
import com.tech.hilt_mvvm.data.preferences.PreferenceProvider
import com.tech.hilt_mvvm.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: ApiInterface, private val pref: PreferenceProvider):SafeApiRequest() {

    fun getUserName():String? {
        return pref.getUserName()
    }

    fun setUserName(name:String) {
        pref.setUserName(name)
    }

    suspend fun fetchProductList(): Response<List<ProductResponse>> {
        return apiInterface.fetchProductList()
    }

    suspend fun fetchCurrentWeather(countryText: String): Response<CurrentWeatherResponse> {
        return apiInterface.fetchCurrentWeather(Constants.API_KEY, countryText, "no")
    }
}