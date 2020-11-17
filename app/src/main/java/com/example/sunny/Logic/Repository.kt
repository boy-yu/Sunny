package com.example.sunny.Logic

import androidx.lifecycle.liveData
import com.example.sunny.Logic.Model.Place
import com.example.sunny.Logic.Network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

/** 仓库层的统一封装入口 */
object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}