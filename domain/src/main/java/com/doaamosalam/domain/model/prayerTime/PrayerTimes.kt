package com.doaamosalam.domain.model.prayerTime

/*
*     "timings": {
      "Fajr": "04:47",
      "Sunrise": "06:13",
      "Dhuhr": "12:05",
      "Asr": "15:26",
      "Sunset": "17:58",
      "Maghrib": "17:58",
      "Isha": "19:15",
      "Imsak": "04:37",
      "Midnight": "00:06",
      "Firstthird": "22:03",
      "Lastthird": "02:08"
* */
data class PrayerTimes(
    val fajr: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val date: String,
    val hijriDate: String,
    val timezone: String
)