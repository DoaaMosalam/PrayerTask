package com.doaamosalam.domain.useCase

import com.doaamosalam.domain.model.nextprayer.NextPrayer
import com.doaamosalam.domain.model.prayerTime.PrayerTimes
import javax.inject.Inject
import java.util.Calendar

class GetNextPrayerUseCase @Inject constructor() {

    operator fun invoke(prayerTimes: PrayerTimes): NextPrayer {

        val cal = Calendar.getInstance()

        val nowSeconds = cal.get(Calendar.HOUR_OF_DAY) * 3600 +
                cal.get(Calendar.MINUTE) * 60 +
                cal.get(Calendar.SECOND)

        val prayers = listOf(
            PrayerEntry("Fajr", "الفجر", prayerTimes.fajr),
            PrayerEntry("Dhuhr", "الظهر", prayerTimes.dhuhr),
            PrayerEntry("Asr", "العصر", prayerTimes.asr),
            PrayerEntry("Maghrib", "المغرب", prayerTimes.maghrib),
            PrayerEntry("Isha", "العشاء", prayerTimes.isha)
        )

        val nextPrayer = prayers.firstOrNull { prayer ->
            timeToSeconds(prayer.time) > nowSeconds
        }

        return if (nextPrayer != null) {

            val targetSeconds = timeToSeconds(nextPrayer.time)

            NextPrayer(
                name = nextPrayer.name,
                nameArabic = nextPrayer.nameArabic,
                time = nextPrayer.time,
                remainingSeconds = (targetSeconds - nowSeconds).toLong()
            )

        } else {

            // If no more prayers today, return Fajr of the next day
            val fajrSeconds = timeToSeconds(prayerTimes.fajr)

            NextPrayer(
                name = "Fajr",
                nameArabic = "الفجر",
                time = prayerTimes.fajr,
                remainingSeconds = (86400 - nowSeconds + fajrSeconds).toLong()
            )
        }
    }

    private fun timeToSeconds(time: String): Int {
        val parts = time.take(5).split(":")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()
        return hours * 3600 + minutes * 60
    }

    private data class PrayerEntry(
        val name: String,
        val nameArabic: String,
        val time: String
    )
}
