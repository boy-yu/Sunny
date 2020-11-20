package com.example.sunny

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**1.提供全局获取Context的方式*/
class SunnyWeatherApplication : Application() {

    companion object {
        //获取令牌
        const val TOKEN = "9Mf6NfpCHgUp2W1E"

        //让Android Studio忽略警告提醒
        @SuppressLint("StaticFieldLeak")
        //定义context变量
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        //调用getApplicationContext()方法,赋值给context变量
        context = applicationContext
    }

}