package com.example.dnd.helper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.dnd.helper.RemindersManager.REMINDER_NOTIFICATION_REQUEST_CODE

fun stopReminder(
    context: Context,
    reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
        PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
    alarmManager.cancel(intent)
}