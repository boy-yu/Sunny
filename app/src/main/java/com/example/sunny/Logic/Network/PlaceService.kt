package com.example.sunny.Logic.Network

import com.example.sunny.Logic.Model.PlaceResponse
import com.example.sunny.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**访问云彩天气城市搜索API的接口*/
interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}