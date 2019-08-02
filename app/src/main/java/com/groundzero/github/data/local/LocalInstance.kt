package com.groundzero.github.data.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class LocalInstance(private val context: Context) {
    fun getSharedPreference(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
}