package ru.magzyumov.talan.utils

import android.content.SharedPreferences
import javax.inject.Inject


class PreferenceHelper @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getIntPreference(parameter: String): Int {
        return sharedPreferences.getInt(parameter, 0)
    }

    fun setIntPreference(parameter: String, value: Int) {
        sharedPreferences.edit().putInt(parameter, value).apply()
    }

    fun getBooleanPreference(parameter: String): Boolean {
        return sharedPreferences.getBoolean(parameter, false)
    }

    fun setBooleanPreference(parameter: String, value: Boolean){
        sharedPreferences.edit().putBoolean(parameter, value).apply()
    }

    fun getStringPreference(parameter: String): String? {
        return sharedPreferences.getString(parameter, null)
    }

    fun setStringPreference(parameter: String, value: String){
        sharedPreferences.edit().putString(parameter, value).apply()
    }

    fun removeStringPreference(parameter: String){
        sharedPreferences.edit().remove(parameter).apply()
    }
}