package com.example.weathertracker

import android.app.Application
import com.example.weathertracker.shared.di.setupKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            setupKoin()
        }
    }
}
