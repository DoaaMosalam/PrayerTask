package com.doaamosalam.prayertask.di

import com.doaamosalam.domain.repo.PrayerTimeRepo
import com.doaamosalam.domain.useCase.GetPrayerTimesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrayerUseCase {

    @Provides
    @Singleton
    fun providePrayerTimeUseCase(prayerTimeRepo: PrayerTimeRepo): GetPrayerTimesUseCase {
        return GetPrayerTimesUseCase(prayerTimeRepo)
    }
}