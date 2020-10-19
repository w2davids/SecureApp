package com.example.secureapp.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.secureapp.R
import com.example.secureapp.lockscreen.LockScreenService

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .commitNow()
        }

        startService(Intent(this, LockScreenService::class.java))
    }

}