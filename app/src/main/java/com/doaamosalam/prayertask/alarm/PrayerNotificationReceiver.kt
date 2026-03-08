package com.doaamosalam.prayertask.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.doaamosalam.prayertask.R
import com.doaamosalam.prayertask.util.Constant

class PrayerNotificationReceiver : BroadcastReceiver() {

//    override fun onReceive(context: Context, intent: Intent) {
//
//        val prayerName = intent.getStringExtra("PRAYER_NAME") ?: "Prayer"
//
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        val notification = NotificationCompat.Builder(context, "prayer_channel")
//            .setContentTitle("Prayer Time")
//            .setContentText("It's time for $prayerName")
//            .setSmallIcon(android.R.drawable.ic_dialog_info)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .build()
//
//        notificationManager.notify(1, notification)
//    }
override fun onReceive(context: Context, intent: Intent) {
    val prayerName = intent.getStringExtra("prayer_name") ?: "Prayer"
    val prayerNameArabic = intent.getStringExtra("prayer_name_arabic") ?: ""

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Create channel
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            Constant.CHANNEL_ID,
            "Prayer Times",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Prayer time notifications"
        }
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("حان وقت الصلاة 🕌")
        .setContentText("$prayerNameArabic - $prayerName")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(prayerName.hashCode(), notification)
}
}