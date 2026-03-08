package com.doaamosalam.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.doaamosalam.data.local.PrayerTimeData.PrayerTimesDao
import com.doaamosalam.data.local.PrayerTimeData.PrayerTimesEntity

@Database(
    entities = [PrayerTimesEntity::class],
    version =1,
    exportSchema = false
)
abstract class PrayerDatabase  : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Schema changes from version 1 to 2
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create image_user table
                database.execSQL(
                    "CREATE TABLE image_user (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "imageUri TEXT NOT NULL)"
                )
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example of adding a column
                database.execSQL("ALTER TABLE image_user ADD COLUMN new_column_name TEXT")
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Handle any schema changes for version 5
            }
        }
    }
}