package com.doaamosalam.domain.repo


import com.doaamosalam.domain.model.prayerTime.PrayerTimes
import com.doaamosalam.domain.util.Resource

interface PrayerTimeRepo {

suspend fun getPrayerTime(city: String, country: String): Resource<PrayerTimes>
}