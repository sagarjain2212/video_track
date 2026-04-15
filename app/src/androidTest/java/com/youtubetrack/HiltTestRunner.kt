package com.youtubetrack

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        // This tells the instrumented test to use Hilt's test application class
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}