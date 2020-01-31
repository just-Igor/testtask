package com.example.testtask.app

import android.app.Application
import com.example.testtask.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(
                utilsModule,
                databaseModule,
                networkModule,
                navigationModule,
                movieModule))
        }
    }

}