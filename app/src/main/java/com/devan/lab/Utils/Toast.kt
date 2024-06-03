package com.devan.lab.Utils

import android.app.Activity
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.devan.lab.R

enum class ToastType {
    SUCCESS,
    ERROR,
    INFO
}

fun getToastColor(activity: Activity, type: ToastType): Int {
    return when (type) {
        ToastType.SUCCESS -> ContextCompat.getColor(activity, R.color.success_color)
        ToastType.ERROR -> ContextCompat.getColor(activity, R.color.error_color)
        ToastType.INFO -> ContextCompat.getColor(activity, R.color.info_color)
    }
}

fun Toast.showCustomToast(message: String, activity: Activity, type: ToastType) {
    val layout = activity.layoutInflater.inflate(
        R.layout.custom_toast_layout,
        activity.findViewById(R.id.toast_container)
    )
    val textView = layout.findViewById<TextView>(R.id.toast_text)
    val buttonAccentBorder = layout.findViewById<FrameLayout>(R.id.button_accent_border)
    val backgroundColor = getToastColor(activity, type)

    textView.text = message
    buttonAccentBorder.setBackgroundColor(backgroundColor)

    this.apply {
        setGravity(Gravity.TOP, 0, -40)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}

class ToastManager {
    companion object {
        private var currentToast: Toast? = null

        fun showToast(message: String, activity: Activity, type: ToastType) {
            currentToast?.cancel()
            currentToast = Toast(activity).apply {
                showCustomToast(message, activity, type)
            }
        }
    }
}
