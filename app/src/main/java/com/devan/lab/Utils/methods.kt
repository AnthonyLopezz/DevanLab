package com.devan.lab.Utils

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable

fun createDynamicDrawable(color: Int, radius: Float): StateListDrawable {
    val shape = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(color)
        cornerRadius = radius
    }

    return StateListDrawable().apply {
        addState(intArrayOf(), shape)
    }
}
