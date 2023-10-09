package com.app.cocktailmachine.ui

import android.content.Context
import android.content.SharedPreferences

object Counter {

    val pref_name = "counter"

    fun getValue(context: Context): Int{
        return context.getSharedPreferences(pref_name, Context.MODE_PRIVATE).getInt(pref_name, 0)
    }

    fun increase(context: Context){
        context.getSharedPreferences(pref_name, Context.MODE_PRIVATE).edit().putInt(pref_name, getValue(context) + 1).apply()
    }

    fun reset(context: Context) {
        context.getSharedPreferences(pref_name, Context.MODE_PRIVATE).edit().putInt(pref_name, 0 ).apply()
    }
}
