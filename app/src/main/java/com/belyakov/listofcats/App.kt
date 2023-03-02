package com.belyakov.listofcats

import android.content.Context
import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class App : MultiDexApplication(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startDIifNeed(this)
    }

    companion object {
        private var isDIStarted = false

        @Synchronized
        fun startDIifNeed(context: Context) {
            if (isDIStarted) {
                return
            }
            startKoin {
                androidContext(context)
                modules( CatsModule.create() )
            }
        }
    }
}