package com.example.secureapp.settings

import android.content.Context
import android.content.SharedPreferences

class SettingsPreferenceManager(private val context: Context) {

    companion object {
        const val PREFS_FILENAME = "com.example.secureapp.settings.prefs"
        const val KEY_PIN_LOCK_ENABLED = "pin_lock_enabled"
    }

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    internal var isPinCodeEnabled: Boolean
        set(value) {
            sharedPrefs.edit()
                .putBoolean(KEY_PIN_LOCK_ENABLED, value)
                .apply()
        }
        get() = sharedPrefs.getBoolean(KEY_PIN_LOCK_ENABLED, false)

    internal fun clear() = sharedPrefs.edit()
        .clear()
        .apply()

}