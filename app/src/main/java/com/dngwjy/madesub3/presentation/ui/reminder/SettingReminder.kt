package com.dngwjy.madesub3.presentation.ui.reminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dngwjy.madesub3.R
import com.dngwjy.madesub3.services.DailyReminderService
import com.dngwjy.madesub3.services.ReleaseReminderServices
import com.dngwjy.madesub3.util.SharedPref
import kotlinx.android.synthetic.main.activity_setting_reminder.*

class SettingReminder : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_reminder)

        sharedPreferences = SharedPref(this)

        loadSettings()

        btn_daily_reminder.setOnClickListener {
            val timeDaily = "07:00"
            DailyReminderService().setRepeatingAlarm(this, timeDaily, btn_daily_reminder.isChecked)
            sharedPreferences.daily = btn_daily_reminder.isChecked
        }

        btn_release_reminder.setOnClickListener {
            val time = "08:00"
            ReleaseReminderServices().setRepeatingAlarm(this, time, btn_daily_reminder.isChecked)
            sharedPreferences.rilis = btn_release_reminder.isChecked
        }
    }

    private fun loadSettings() {
        btn_daily_reminder.isChecked = sharedPreferences.daily
        btn_release_reminder.isChecked = sharedPreferences.rilis
    }
}
