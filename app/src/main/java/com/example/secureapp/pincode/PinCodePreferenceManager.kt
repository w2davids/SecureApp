package com.example.secureapp.pincode

import android.content.Context
import android.content.SharedPreferences

class PinCodePreferenceManager(private val context: Context) {

    companion object {
        const val PREFS_FILENAME = "com.example.secureapp.pincode.prefs"
        const val KEY_PIN_CODE = "pin_code"
    }

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    internal var pinCode: String? = null
        set(value) {
            sharedPrefs.edit()
                .putString(KEY_PIN_CODE, value)
                .apply()
            field = value
        }
        get() = sharedPrefs.getString(KEY_PIN_CODE, null)

    internal fun clear() = sharedPrefs.edit()
        .clear()
        .apply()
}