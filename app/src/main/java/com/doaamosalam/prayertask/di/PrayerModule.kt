package com.doaamosalam.prayertask.di

import com.doaamosalam.data.remote.APIPrayerService
import com.doaamosalam.data.repository.PrayerTimeRepoImpl
import com.doaamosalam.domain.repo.PrayerTimeRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrayerModule {
    @Provides
    @Singleton
    fun providePrayerTimeRepo(apiService: APIPrayerService): PrayerTimeRepo {
        return PrayerTimeRepoImpl(apiService)
    }
}