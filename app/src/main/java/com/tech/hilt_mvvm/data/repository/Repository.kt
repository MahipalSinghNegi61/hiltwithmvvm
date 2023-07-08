package com.tech.hilt_mvvm.data.repository

import com.tech.hilt_mvvm.data.network.ApiInterface
import com.tech.hilt_mvvm.data.preferences.PreferenceProvider

class Repository(private val preferenceProvider: PreferenceProvider, private val apiInterface: ApiInterface) {

}