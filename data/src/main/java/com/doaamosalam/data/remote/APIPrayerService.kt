package com.doaamosalam.data.remote


import com.doaamosalam.data.dto.PrayerApiResponse
import com.doaamosalam.data.dto.PrayerTimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIPrayerService {


    @GET("v1/timingsByCity")
    suspend fun getPrayerTimes(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("method") method: Int = 5
    ): PrayerApiResponse
}