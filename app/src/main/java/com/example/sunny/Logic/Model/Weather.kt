package com.example.sunny.Logic.Model

/**将Realtime和Daily,Hourly对象封装起来*/

class Weather(
    val realtime: RealtimeResponse.Realtime,
    val daily: DailyResponse.Daily,
    val hourly: HourlyResponse.Hourly
    )