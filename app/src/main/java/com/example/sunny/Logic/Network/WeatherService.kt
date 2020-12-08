package com.example.sunny.Logic.Network

import com.example.sunny.Logic.Model.DailyResponse
import com.example.sunny.Logic.Model.HourlyResponse
import com.example.sunny.Logic.Model.RealtimeResponse
import com.example.sunny.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**16.访问天气信息API的Retrofit接口*/
interface WeatherService {
    //获取实时天气信息
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String):
    //对应定义好的RealtimeResponse数据模型
            Call<RealtimeResponse>

    //获取未来15天的天气信息
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json?dailysteps=15")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String):
    //对应定义好的DailyResponse数据模型
            Call<DailyResponse>

    //获取未来24小时的天气信息
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/hourly.json?hourlysteps=24")
    fun getHourlyWeather(@Path("lng") lng: String, @Path("lat") lat: String):
    //对应定义好的DailyResponse数据模型
            Call<HourlyResponse>
}