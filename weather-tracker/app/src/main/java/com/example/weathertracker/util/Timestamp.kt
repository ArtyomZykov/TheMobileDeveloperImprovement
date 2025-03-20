package com.example.weathertracker.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Timestamp {

    fun convertUnixTimestampToDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        val date = Date(timestamp * 1_000) // Multiply by 1000 because the timestamp is in seconds
        return sdf.format(date)
    }
}
