package com.example.sunny

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**提供全局获取Context的方式*/
class SunnyWeatherApplication : Application() {

    companion object {
        const val TOKEN = "9Mf6NfpCHgUp2W1E"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}