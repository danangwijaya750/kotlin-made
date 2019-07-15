package com.dngwjy.madesub3.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 * Created by wijaya on 26/06/19
 */
fun Context.toastCnt(msg: String) {
    val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}