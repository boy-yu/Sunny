package com.example.sunny.Logic.Model

import com.google.gson.annotations.SerializedName

/**2.查询全球城市
 * 获取全球城市的数据模型
 */
/** 查询城市的JSON数据格式
 * {"status":"ok","query":"北京",
 * "places":[
 * {"name":"北京","location":{"lat":39.9041999,"lng":116.4073963},
 * "place_id":"g-ChIJuSwU55ZS8DURiqkPryBWYrk","name":"\u5317\u4eac\u5e02","formatted_address":"\u4e2d\u56fd\u5317\u4eac\u5e02"},
 * {"id":"B000A83M61","name":"\u5317\u4eac\u897f\u7ad9","formatted_address":"\u4e2d\u56fd \u5317\u4eac\u5e02 \u4e30\u53f0\u533a \u83b2\u82b1\u6c60\u4e1c\u8def118\u53f7","location":{"lat":39.89491,"lng":116.322056},
 * "place_id":"a-B000A83M61"},
 * {"id":"B000A833V8","name":"\u5317\u4eac\u5317\u7ad9","formatted_address":"\u4e2d\u56fd \u5317\u4eac\u5e02 \u897f\u57ce\u533a \u5317\u6ee8\u6cb3\u8def1\u53f7","location":{"lat":39.944876,"lng":116.353063},
 * "place_id":"a-B000A833V8"},
 * {"id":"B000A350CB","name":"\u5317\u4eac\u4e1c\u7ad9","formatted_address":"\u4e2d\u56fd \u5317\u4eac\u5e02 \u671d\u9633\u533a \u767e\u5b50\u6e7e\u8def7\u53f7","location":{"lat":39.90242,"lng":116.485079},
 * "place_id":"a-B000A350CB"},
 * {"id":"B000A83C36","name":"\u5317\u4eac\u7ad9","formatted_address":"\u4e2d\u56fd \u5317\u4eac\u5e02 \u4e1c\u57ce\u533a \u6bdb\u5bb6\u6e7e\u80e1\u540c\u753213\u53f7","location":{"lat":39.902779,"lng":116.427694},
 * "place_id":"a-B000A83C36"}]}
 */
//                      状态(ok表示成功)     JSON数组(包含几个与我们查询的关键字关键系度比较高的地区信息)
class PlaceResponse(val status: String, val places: List<Place>)

class Place(
    val name: String,//该地区的名字
    val location: Location,//该地区的经纬度
    //转译成Kotlin能识别的对象名
    @SerializedName("formatted_address") val address: String//该地区的地址
)
//地区的经纬度
class Location(val lng: String, val lat: String)