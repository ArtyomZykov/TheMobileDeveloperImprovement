package com.example.weathertracker.shared.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

object Timestamp {

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun convertUnixTimestampToDate(timestamp: Long): String = Instant
        .fromEpochSeconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .format(LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd HH:mm") })
}
