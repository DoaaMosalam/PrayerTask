package com.doaamosalam.domain.useCase


import com.doaamosalam.domain.model.prayerTime.PrayerTimes
import com.doaamosalam.domain.repo.PrayerTimeRepo
import com.doaamosalam.domain.util.Resource
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor(
    private val prayerTimeRepo: PrayerTimeRepo
) {
    suspend operator fun invoke (city: String, country: String): Resource<PrayerTimes> {
        return prayerTimeRepo.getPrayerTime(city, country)
    }
}