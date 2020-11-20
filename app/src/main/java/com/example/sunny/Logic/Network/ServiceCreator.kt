package com.example.sunny.Logic.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** 4.创建Retrofit构造器 */
object ServiceCreator {

    //指定Retrofit的根路径
    private const val BASE_URL = "https://api.caiyunapp.com/"

    //构建Retrofit对象,使用private修饰
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //带参数的ceate()方法
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //不带参数的ceate()方法
    inline fun <reified T> create(): T = create(T::class.java)
}