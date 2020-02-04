package com.example.testtask.app

import android.app.Application
import com.example.testtask.di.databaseModule
import com.example.testtask.di.movieModule
import com.example.testtask.di.navigationModule
import com.example.testtask.di.networkModule
import com.example.testtask.di.utilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

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
