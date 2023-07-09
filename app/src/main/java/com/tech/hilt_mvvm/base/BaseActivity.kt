package com.tech.hilt_mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tech.hilt_mvvm.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
