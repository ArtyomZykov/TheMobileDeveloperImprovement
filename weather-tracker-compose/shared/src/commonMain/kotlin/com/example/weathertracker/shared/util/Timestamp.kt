package com.example.weathertracker.shared.util

object Timestamp {

    fun convertUnixTimestampToDate(timestamp: Long): String {
        return timestamp.toString()

//        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
//        val netDate = Date(timestamp * 1_000)
//        return sdf.format(netDate)

//        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
//        sdf.timeZone = TimeZone.getDefault()
//        val date = Date(timestamp * 1_000) // Multiply by 1000 because the timestamp is in seconds
//        return sdf.format(date)
    }
}