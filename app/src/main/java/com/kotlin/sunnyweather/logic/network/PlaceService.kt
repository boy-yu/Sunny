package com.kotlin.sunnyweather.logic.network

import com.kotlin.sunnyweather.SunnyWeatherApplication
import com.kotlin.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**接入API的接口*/
interface PlaceService {
    @GET("V2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String):Call<PlaceResponse>
}