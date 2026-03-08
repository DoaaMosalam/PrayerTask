package com.doaamosalam.data.mapper
import com.doaamosalam.data.dto.PrayerApiResponse
import com.doaamosalam.domain.model.PrayerTime.PrayerTimes



fun PrayerApiResponse.toDomain(): PrayerTimes {
    return PrayerTimes(
        fajr = data.timings.fajr,
        dhuhr = data.timings.dhuhr,
        asr = data.timings.asr,
        maghrib = data.timings.maghrib,
        isha = data.timings.isha,
        date = data.date.readable,
        hijriDate = data.date.hijri.date,
        timezone = data.meta.timezone
    )
}