package com.natiqhaciyef.composable_widget.component.worker.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.natiqhaciyef.composable_widget.R
import com.natiqhaciyef.customopensourcecomposablewidgets.MainActivity
import com.natiqhaciyef.composable_widget.component.common.Resource

object NotificationSender {
    fun sendNotification(
        context: Context,
        title: String,
        description: String,
        imageId: Int = R.drawable.ic_launcher_foreground,
        isAutoCancellable: Boolean = true
    ): Resource<String> {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "NEWS"
            val channelName = "News Alert Channel"
            val channelDescription = "Channel for News Alerts"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                this.description = channelDescription
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)

            val pending =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(context, "NEWS")
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(imageId)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(isAutoCancellable)
                .setContentIntent(pending)

            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return Resource.error("Permission not granted", null)
                }
                notify(1, notification.build())
            }
        }
        return Resource.success("")
    }


}