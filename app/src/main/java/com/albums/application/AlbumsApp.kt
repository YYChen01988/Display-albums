package com.albums.application

import android.app.Application
import com.albums.BuildConfig
import com.albums.albums.injection.albumsModule
import com.albums.core.injections.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AlbumsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.ERROR)
            }
            androidContext(this@AlbumsApp)
            modules(listOf(networkModule, albumsModule))
        }
    }
}
