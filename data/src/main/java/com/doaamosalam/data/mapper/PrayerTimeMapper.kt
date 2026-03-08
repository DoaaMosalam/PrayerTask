package com.doaamosalam.data.mapper

import com.doaamosalam.data.local.PrayerTimeData.PrayerTimesEntity
import com.doaamosalam.domain.model.prayerTime.PrayerTimes

fun PrayerTimesEntity.toDomain(): PrayerTimes {
    return PrayerTimes(
        fajr = fajr,
        dhuhr = dhuhr,
        asr = asr,
        maghrib = maghrib,
        isha = isha,
        date = date,
        hijriDate = hijriDate,
        timezone = timezone
    )
}

fun PrayerTimes.toEntity(): PrayerTimesEntity {
    return PrayerTimesEntity(
        id = 1,
        date = date,
        fajr = fajr,
        dhuhr = dhuhr,
        asr = asr,
        maghrib = maghrib,
        isha = isha,
        hijriDate = hijriDate,
        timezone = timezone
    )
}