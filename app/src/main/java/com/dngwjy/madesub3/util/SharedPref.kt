package com.dngwjy.madesub3.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by wijaya on 01/07/19
 */
class SharedPref(context: Context) {
    companion object {
        val PREFS_FILENAME = "com.dng.wjy.pref"
        val DAILY = "daily"
        val RELEASE = "rilis"
    }

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var daily: Boolean
        get() = prefs.getBoolean(DAILY, false)
        set(value) = prefs.edit().putBoolean(DAILY, value).apply()

    var rilis: Boolean
        get() = prefs.getBoolean(RELEASE, false)
        set(value) = prefs.edit().putBoolean(RELEASE, value).apply()
}