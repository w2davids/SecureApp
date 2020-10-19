package com.example.secureapp

import android.app.Application
import timber.log.Timber

class SecureApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}