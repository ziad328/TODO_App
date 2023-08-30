package com.example.todo.utils

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

fun showDatePickerDialog(context: Context, callback: (String, Calendar) -> Unit) {
    val dialog = DatePickerDialog(context)
    dialog.setOnDateSetListener { datePicker, year, month, day ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        callback("$day/${month + 1}/$year", calendar)

    }
    dialog.show()
}


inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}


fun getCurrentLanguage(context: Context): String {
    val configuration = context.resources.configuration
    return configuration.locales[0].language
}