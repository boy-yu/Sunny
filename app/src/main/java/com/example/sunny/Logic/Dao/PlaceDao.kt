package com.example.sunny.Logic.Dao


import android.content.Context
import androidx.core.content.edit
import com.example.sunny.Logic.Model.Place
import com.example.sunny.SunnyWeatherApplication
import com.google.gson.Gson
/**26.储存数据*/
object PlaceDao {

    //用于将Place对象储存到sharedPreferences文件中
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            //通过Gson将Place对象转换成Json字符串,然后用字符串存储的方式来保存数据
            putString("place", Gson().toJson(place))
        }
    }

    //读取数据
    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    //判断是否有数据被存储
    fun isPlaceSaved() = sharedPreferences().contains("place")

    //储存文件
    private fun sharedPreferences() =
        SunnyWeatherApplication.context.
        getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

}