package com.phoenixdev.nudge

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NudgeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize WorkManager
        val workManagerConfig = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
        WorkManager.initialize(this, workManagerConfig)
    }
}
