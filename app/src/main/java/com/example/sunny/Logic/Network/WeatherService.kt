package com.example.sunny.Logic.Network

import com.example.sunny.Logic.Model.DailyResponse
import com.example.sunny.Logic.Model.RealtimeResponse
import com.example.sunny.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**访问天气信息API的Retrofit接口*/
interface WeatherService {
    //获取实时天气信息
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    //获取未来几天的天气信息
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>
}