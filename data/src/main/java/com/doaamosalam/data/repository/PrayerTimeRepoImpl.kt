package com.doaamosalam.data.repository

import com.doaamosalam.data.local.PrayerTimeData.PrayerTimesDao
import com.doaamosalam.data.remote.APIPrayerService
import com.doaamosalam.domain.model.prayerTime.PrayerTimes
import com.doaamosalam.domain.repo.PrayerTimeRepo
import com.doaamosalam.domain.util.Resource

import com.doaamosalam.data.mapper.toDomain
import com.doaamosalam.data.mapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// data/repository/PrayerTimeRepoImpl.kt
class PrayerTimeRepoImpl @Inject constructor(
    private val api: APIPrayerService,
    private val dao: PrayerTimesDao
) : PrayerTimeRepo {

    override suspend fun getPrayerTime(
        city: String,
        country: String
    ): Resource<PrayerTimes> {
        val cached = dao.getLatestPrayerTimes()

        if (cached != null) {
            return Resource.Success(cached.toDomain())
        }
        return try {
        // Fetch prayer times from the API
            val response = api.getPrayerTimes(city, country, method = 5)
            val prayerTimes = response.toDomain()

           // Cache the latest prayer times in the local database
            dao.insertPrayerTimes(prayerTimes.toEntity())
            Resource.Success(prayerTimes)

        } catch (e: Exception) {

            val cached = withContext(Dispatchers.IO) { dao.getLatestPrayerTimes() }
            if (cached != null) {
                Resource.Success(cached.toDomain())
            } else {
                Resource.Error(e)
            }
        }
    }
}