package com.doaamosalam.domain.util

import java.util.Calendar

fun convertPrayerTimeToMillis(time: String): Long {

    val parts = time.split(":")
    val hour = parts[0].toInt()
    val minute = parts[1].toInt()

    val calendar = Calendar.getInstance()

    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, 0)

    return calendar.timeInMillis
}
