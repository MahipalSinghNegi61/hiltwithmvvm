package com.tech.hilt_mvvm

import android.app.Application
import com.tech.hilt_mvvm.di.AppModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}