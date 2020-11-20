package com.example.sunny.Logic


import androidx.lifecycle.liveData
import com.example.sunny.Logic.Dao.PlaceDao
import com.example.sunny.Logic.Model.Place
import com.example.sunny.Logic.Model.Weather
import com.example.sunny.Logic.Network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/** 6.仓库层的统一封装入口 */
object Repository {

    /****************************************6.搜索城市****************************************/
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {

        //调用了SunnyWeatherNetwork的searchPlaces()函数来搜索城市数据
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)

        if (placeResponse.status == "ok") {//如果服务器想想也的状态是ok
            val places = placeResponse.places
            //包装获取的城市数据列表
            Result.success(places)
        } else {//如果服务器想想也的状态不是ok
            //包装一个异常信息
            Result.failure(
                RuntimeException(
                    "response status is ${placeResponse.status}"
                )
            )
        }
    }
    /****************************************************************************************************/

    /****************************************18.更新天气****************************************/
    //用于刷新天气的方法
    fun refreshWeather(lng: String, lat: String, placeName: String) = fire(Dispatchers.IO) {
        //运用携程作用域让两个方法并发执行(可以提升程序运行效率)
        coroutineScope {
            //获取实时的天气信息
            val deferredRealtime = async {//在async函数中发起网络请求
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            //获取未来几天的天气信息
            val deferredDaily = async {//在async函数中发起网络请求
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }

            //保证两个网络请求都成功响应之后才会执行下一步
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()


            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {//判断两个的状态是否都为ok
                //将realtime和daily的对象取出并封装到Weather对象中
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                //使用Result.success()方法包装weather对象
                Result.success(weather)
            } else {//如果服务器想想也的状态不是ok
                Result.failure(//包装一个异常信息
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    /****************************************************************************************************/



    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        //调用 liveData()函数
        liveData<Result<T>>(context) {
            //在liveData()函数内统一进行try catch处理
            val result = try {
                //传入Lambda表达式
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            //获取Lambda表达式执行的结果并调用emit()方法发射出去
            emit(result)
        }


    /**27.存储/读取接口封装*/
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}