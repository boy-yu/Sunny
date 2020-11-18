package com.example.sunny.Logic.Model

import com.google.gson.annotations.SerializedName

/**查询全球城市
 * 获取全球城市的数据模型
 */
class PlaceResponse(val status: String, val places: List<Place>)

class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

class Location(val lng: String, val lat: String)