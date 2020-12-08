package com.kotlin.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName
/**获取Json返回的城市数据*/

data class PlaceResponse(val status: String, val places: List<Place>) {}

data class Place(
        val name: String, val location: Location,
        @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)