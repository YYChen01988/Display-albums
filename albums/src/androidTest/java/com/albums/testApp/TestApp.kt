package com.albums.testApp

import android.app.Application
import com.albums.R
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
        startKoin {}
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}