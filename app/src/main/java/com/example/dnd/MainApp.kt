package com.example.dnd

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.example.dnd.fragments.SplashFragment
import com.example.dnd.helper.dbHelper.ProfileDatabase
import kotlin.math.log

class MainApp : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {

            var db = Room
                .databaseBuilder(this, ProfileDatabase::class.java, "profiledb")
                .build()
        } catch (e: Exception) {
            Log.e("TAG", "onCreate: ${e}")
        }
        createNotificationsChannels()
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.container, SplashFragment())
            .commitAllowingStateLoss()

    }

    private fun createNotificationsChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "12112",
                "Reminder-sound",
                NotificationManager.IMPORTANCE_HIGH
            )
            ContextCompat.getSystemService(this, NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }
}