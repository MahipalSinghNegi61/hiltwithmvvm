package com.tech.hilt_mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel:ViewModel() {
    var progressBarVisibility = MutableLiveData<Boolean>()
    var errorMsg = MutableLiveData<String>()
}