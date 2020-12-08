package com.kotlin.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**设置全局Context
 * 在任何位置都可以用 SunnyWeatherApplication.context 来获取Context对象
 */
class SunnyWeatherApplication : Application() {

    companion object {

        //云彩的天气令牌
        const val TOKEN = "9Mf6NfpCHgUp2W1E"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    /**项目启动的时候给context赋值*/
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}