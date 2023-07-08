package com.tech.hilt_mvvm.data.preferences

import android.content.Context
import android.content.SharedPreferences

const val userName = "USERNAME"

class PreferenceProvider(context: Context) {

    var preferences: SharedPreferences = context.getSharedPreferences(context.packageName,
        Context.MODE_PRIVATE
    )

    fun setUserName(name:String) {
        preferences.edit()
            .putString(userName, name)
            .apply()
    }

    fun getUserName():String? {
        return preferences.getString(userName, null)
    }
}