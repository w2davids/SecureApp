package com.example.secureapp.lockscreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.secureapp.settings.SettingsActivity

class LockScreenIntentReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_SCREEN_OFF)
            || intent?.action.equals(Intent.ACTION_SCREEN_ON)
            || intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)
        ) {
            context?.let {
                startLockScreen(it)
            }
        }
    }

    private fun startLockScreen(context: Context) {
        val mIntent = Intent(context, SettingsActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(mIntent)
    }
}