package com.devan.lab.Utils

import android.app.AlertDialog
import android.content.Context

fun showAlert(message: String, activity: Context) {
    val builder = AlertDialog.Builder(activity)
    builder.setTitle("Error")
    builder.setMessage(message)
    builder.setPositiveButton("Accept", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

fun showConfirm(message: String, activity: Context) {
    val builder = AlertDialog.Builder(activity)
    builder.setTitle("Success")
    builder.setMessage(message)
    builder.setPositiveButton("Accept", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}