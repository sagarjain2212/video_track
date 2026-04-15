package com.youtubetrack

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YoutubeTrackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}