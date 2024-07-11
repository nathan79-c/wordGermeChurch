package com.example.wordgermechurch

import android.app.Application
import com.example.wordgermechurch.data.AppContainer
import com.example.wordgermechurch.data.AppDataContainer

class VersetApplication : Application(){
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}