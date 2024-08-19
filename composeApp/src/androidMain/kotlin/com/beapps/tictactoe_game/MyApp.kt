package com.beapps.tictactoe_game

import android.app.Application
import com.beapps.tictactoe_game.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApp)
        }
    }
}