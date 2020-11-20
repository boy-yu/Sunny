package com.example.sunny.Logic.Network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/** 5.网络数据访问入口 */
object SunnyWeatherNetwork {
    /********************17.对WeatherService接口进行封装********************/
    //创建一个weatherService接口的动态代理对象WeatherService
    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    //定义一个getDailyWeather()方法,发起获取实时天气数据
    suspend fun getDailyWeather(lng: String, lat: String) =
        weatherService.getDailyWeather(lng, lat).await()

    //定义一个getRealtimeWeather()方法,发起获取未来几天的天气数据
    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).await()

    /***********************************************************************************/

    /********************5.对PlaceService接口进行封装********************/
    //创建一个PlaceService接口的动态代理对象placeService
    private val placeService = ServiceCreator.create<PlaceService>()

    //定义一个searchPlaces()方法,发起搜索城市数据请求
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    /***********************************************************************************/


    //await()是一个挂起函数,给它声明了一个泛型 T
    private suspend fun <T> Call<T>.await(): T {//并将 await()函数定义成了 Call<T>的扩展函数,这样所有返回值是 Call 类型的 Retrofit 网络请求接口就都可以直接调用 await()函数了
        return suspendCoroutine { continuation ->//await()函数中使用了 suspendCoroutine 函数来挂起当前协程,并且由于扩展函数的原因,我们现在拥有了call对象的上下文
            enqueue(object : Callback<T> {//调用enqueue()方法让Retrofit发起网络请求
                override fun onResponse(call: Call<T>, response: Response<T>) {//在 onResponse()进行回调
                    val body = response.body()//body()方法解析出来
                    if (body != null) continuation.resume(body)//解析出来的对象是可能为空的
                    else continuation.resumeWithException(//如果为空的话,这里的做法是手动抛出一个异常
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}