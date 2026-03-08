package com.doaamosalam.prayertask.di

import android.content.Context
import com.doaamosalam.data.local.PrayerTimeData.PrayerTimesDao
import com.doaamosalam.data.remote.APIPrayerService
import com.doaamosalam.data.repository.PrayerTimeRepoImpl
import com.doaamosalam.domain.repo.PrayerTimeRepo
import com.doaamosalam.prayertask.alarm.PrayerNotificationScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrayerModule {
    @Provides
    @Singleton
    fun providePrayerTimeRepo(
        apiService: APIPrayerService,
        dao: PrayerTimesDao
    ): PrayerTimeRepo {
        return PrayerTimeRepoImpl(
            apiService,
            dao)
    }

    @Provides
    @Singleton
    fun providePrayerNotificationScheduler(
        @ApplicationContext context: Context
    ): PrayerNotificationScheduler = PrayerNotificationScheduler(context)
}