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
import com.dngwjy.madesub3.data.remote.model.MovieResponse
import com.dngwjy.madesub3.presentation.ui.detail.DetailActivity
import com.dngwjy.madesub3.util.Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by wijaya on 01/07/19
 */
class ReleaseReminderServices : BroadcastReceiver() {
    private val TIME_FORMAT = "HH:mm"
    private val CHANNEL_ID = "Channel_2"
    private val CHANNEL_NAME = "Release Reminder"
    private val GROUP_KEY_RELEASE = "group_key_release"
    private val ID_RELEASE_REMINDER = 102
    internal var simpleDateFormat: SimpleDateFormat? = null
    internal var currentDate: String? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        currentDate = simpleDateFormat!!.format(Date())
        Log.d("RELASEMOVIE", "Data = $currentDate")
        ApiCreator.createService().creator().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("RELASEMOVIE", response.body()!!.results.get(0).releaseDate + "")
                checkRelease(response.body()!!.results, context!!)
            }

        })

        Log.d("Reminder", "Notif Release")
    }

    private fun checkRelease(detailMovieList: List<MovieResponse.Result>, context: Context) {
        val newReleaseMovie = ArrayList<MovieResponse.Result>()

        for (detailMovie in detailMovieList) {
            if (detailMovie.releaseDate.equals(currentDate)) {
                newReleaseMovie.add(detailMovie)
            }
        }
        if (newReleaseMovie.size > 0) {
            sendNotification(newReleaseMovie, context)
        } else {
            showNoReleaseNotification(context)
        }
    }

    fun setRepeatingAlarm(context: Context, time: String, isStart: Boolean?) {
        if (isStart!!) {
            val startReminder: Long
            if (isDateInvalid(time, TIME_FORMAT)) return

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReleaseReminderServices::class.java)

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

            val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMINDER, intent, 0)
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                startReminder,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )

            Toast.makeText(context, context.resources.getString(R.string.daily_reminder_on), Toast.LENGTH_SHORT)
                .show()
        } else {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReleaseReminderServices::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMINDER, intent, 0)

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

    private fun sendNotification(detailMovieList: List<MovieResponse.Result>, context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var builder: NotificationCompat.Builder

        for (i in 0..5) {

            val moveDetailMovie = Intent(context, DetailActivity::class.java)
            moveDetailMovie.putExtra(Helper.MOVIE_PARCEL, detailMovieList[i])
            moveDetailMovie.action = java.lang.Long.toString(System.currentTimeMillis())

            val pendingIntent =
                PendingIntent.getActivity(context, 0, moveDetailMovie, PendingIntent.FLAG_UPDATE_CURRENT)

            builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(detailMovieList[i].title)
                .setContentText(detailMovieList[i].title + context.resources.getString(R.string.has_been_released))
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentIntent(pendingIntent)
                .setGroup(GROUP_KEY_RELEASE)
                .setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

                builder.setChannelId(CHANNEL_ID)

                notificationManager.createNotificationChannel(channel)
            }

            val notification = builder.build()

            notificationManager.notify(i, notification)
        }

    }

    private fun showNoReleaseNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val reminderSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon_background)
            .setContentTitle(context.getString(R.string.no_new_movie))
            .setContentText(context.getText(R.string.sorry_no_movie_today))
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

        notificationManager.notify(ID_RELEASE_REMINDER, notification)
    }
}