package com.doaamosalam.data.repository

import com.doaamosalam.data.remote.APIPrayerService
import com.doaamosalam.domain.model.prayerTime.PrayerTimes
import com.doaamosalam.domain.repo.PrayerTimeRepo
import com.doaamosalam.domain.util.Resource

import com.doaamosalam.data.mapper.toDomain
import javax.inject.Inject

class PrayerTimeRepoImpl @Inject constructor(
    private val api: APIPrayerService
) : PrayerTimeRepo {

    override suspend fun getPrayerTime(
        city: String,
        country: String
    ): Resource<PrayerTimes> {
        return try {
            val response = api.getPrayerTimes(
                city = city,
                country = country,
                method = 5
            )
            Resource.Success(response.toDomain())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}