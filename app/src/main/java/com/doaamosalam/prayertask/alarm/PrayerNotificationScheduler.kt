package com.doaamosalam.prayertask.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.doaamosalam.domain.model.nextprayer.NextPrayer


class PrayerNotificationScheduler(private val context: Context) {
    fun scheduleNextPrayerNotification(nextPrayer: NextPrayer) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, PrayerNotificationReceiver::class.java).apply {
            putExtra("prayer_name", nextPrayer.name)
            putExtra("prayer_name_arabic", nextPrayer.nameArabic)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            nextPrayer.name.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Calculate trigger time
        val triggerTime = System.currentTimeMillis() + (nextPrayer.remainingSeconds * 1000)

// test trigger time after 10 seconds for demonstration
//        val triggerTime = System.currentTimeMillis() + 10_000
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }
}
