package com.app.cocktailmachine.model

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

    companion object {
        private var instance: SharedPreferenceHelper? = null

        fun getInstance(context: Context): SharedPreferenceHelper {
            if (instance == null) {
                instance = SharedPreferenceHelper(context)
            }
            return instance!!
        }
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun saveBool(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue) ?: defaultValue
    }

    fun getBool(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue) ?: defaultValue
    }

    fun updateGrocerySpots(){
        for (grocery in Grocery.values())
        {
            grocery.spot = getInt(grocery.name + "Spot").toByte()
            grocery.available = getBool(grocery.name + "Available")
        }
    }
}
