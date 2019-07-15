package com.dngwjy.madesub3.services

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dngwjy.madesub3.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by wijaya on 01/07/19
 */
class DailyReminderService : BroadcastReceiver() {

    private val TIME_FORMAT = "HH:mm"
    private val CHANNEL_ID = "Channel_1"
    private val CHANNEL_NAME = "Daily Reminder"
    private val ID_DAILY_REMINDER = 100
    override fun onReceive(context: Context?, intent: Intent?) {
        showNotification(context!!)
        Toast.makeText(context, "Notif WO!!", Toast.LENGTH_LONG).show()
        Log.d("Reminder", "Notif Daily")
    }

    fun setRepeatingAlarm(context: Context, time: String, isStart: Boolean?) {

        if (isStart!!) {
            val startReminder: Long

            if (isDateInvalid(time, TIME_FORMAT)) return

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, DailyReminderService::class.java)

            val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val now = Calendar.getInstance()
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
            calendar.set(Calendar.SECOND, 0)

            if (calendar.timeInMillis <= now.timeInMillis)
                startReminder = calendar.timeInMillis + (AlarmManager.INTERVAL_DAY + 1)
            else
                startReminder = calendar.timeInMillis


            val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0)
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                startReminder,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )

            Toast.makeText(context, context.resources.getString(R.string.daily_reminder_on), Toast.LENGTH_SHORT).show()
        } else {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, DailyReminderService::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0)

            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent)
                Toast.makeText(context, context.resources.getString(R.string.daily_reminder_of), Toast.LENGTH_SHORT)
                    .show()

            }

        }

    }

    private fun isDateInvalid(time: String, timeFormat: String): Boolean {
        try {
            val dateFormat = SimpleDateFormat(timeFormat, Locale.getDefault())
            dateFormat.isLenient = false
            dateFormat.parse(time)
            return false
        } catch (e: ParseException) {
            return true
        }

    }

    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val reminderSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(context.getString(R.string.we_miss_you))
            .setContentText(context.getText(R.string.we_miss_you))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 3000, 1000, 1000, 3000))
            .setSound(reminderSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 3000, 1000, 1000, 3000)

            builder.setChannelId(CHANNEL_ID)

            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManager.notify(ID_DAILY_REMINDER, notification)
    }


}