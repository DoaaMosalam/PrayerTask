package com.doaamosalam.domain.model.prayerTime

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