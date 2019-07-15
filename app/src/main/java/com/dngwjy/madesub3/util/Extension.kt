package com.dngwjy.madesub3.util

import android.util.Log

/**
 * Created by wijaya on 25/06/19
 */
inline fun <reified T> T.logD(msg: String?) = msg.let {
    Log.d(T::class.java.simpleName, it)
}

inline fun <reified T> T.logE(msg: String?) = msg.let {
    Log.e(T::class.java.simpleName, it)
}