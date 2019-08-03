package com.groundzero.github.data.local

import android.content.Context
import android.content.SharedPreferences

class LocalRepository(private val localInstance: LocalInstance) {

    fun setData(key: String, value: String): SharedPreferences.Editor =
        localInstance.getSharedPreference().edit().putString(key, value)

    fun getData(key: String): String? = localInstance.getSharedPreference().getString(key, "")
}