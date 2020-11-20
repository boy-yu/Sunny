package com.example.sunny.Logic.Network

import com.example.sunny.Logic.Model.PlaceResponse
import com.example.sunny.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**3.访问云彩天气城市搜索API的接口*/
interface PlaceService {
    //调用searchPlaces方法的时候Retrofit就会自动发送GET请求,去访问@GET注解中配置的地址.
    //另外两个参数不会产生改变,固定写在@GET中即可
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")

    //其中,城市搜索的API知有query这个参数需要动态指定,使用@Query注解的方式来实现
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>//声明成Call<PlaceResponse> Retrofit会将服务器返回的JSON自动解析成PlaceResponse对象
}