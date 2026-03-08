package com.doaamosalam.prayertask.di

import android.content.Context
import androidx.room.Room
import com.doaamosalam.data.local.PrayerDatabase
import com.doaamosalam.data.local.PrayerTimeData.PrayerTimesDao
import com.doaamosalam.prayertask.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// di/DatabaseModule.kt
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePrayerDatabase(
        context: Context
    ): PrayerDatabase = Room.databaseBuilder(
        context,
        PrayerDatabase::class.java,
        Constant.DATABASE_NAME
    ).addMigrations(
        PrayerDatabase.MIGRATION_1_2,
        PrayerDatabase.MIGRATION_2_3,
        PrayerDatabase.MIGRATION_3_4,
        PrayerDatabase.MIGRATION_4_5
    )
        .fallbackToDestructiveMigration()  // This will destroy and recreate the database
        .build()

    @Provides
    @Singleton
    fun providePrayerTimesDao(
        database: PrayerDatabase
    ): PrayerTimesDao = database.prayerTimesDao()
}