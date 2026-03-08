package com.doaamosalam.data.local.PrayerTimeData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface PrayerTimesDao {

    @Query("SELECT * FROM prayer_times WHERE id = 1")
    suspend fun getLatestPrayerTimes(): PrayerTimesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayerTimes(entity: PrayerTimesEntity)
}