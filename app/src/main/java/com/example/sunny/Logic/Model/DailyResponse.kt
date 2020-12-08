package com.example.sunny.Logic.Model

import com.google.gson.annotations.SerializedName
import java.util.*

/**15.显示天气信息
 * 获取未来几天天气的数据模型
 */

/**简化的JSON
 * {
 *  "status":"ok",
 *  "result":{
 *      "daily":{
 *          "temperature":[{"max":25.7,"min"20.3},...],
 *          "skycon":[{"value":"CLOUDY","data":"2019-10-20T00:00+8:00"},...],
 *          "life_index":{
 *          "coldRisk":[{"desc":"易发"},...],
 *          "carWashing":[{"desc":"适宜"},...],
 *          "ultraviolet":[{"desc":"无"},...],
 *          "dressing":[{"desc":"舒适"},...],
 *          }
 */

/**
 * 返回的数据全部都是数组形式的,
 * 数组中的每一个原始都对应着一天的数据,
 * 我们可以用List集合对JSON中的数组元素进行映射
 */
data class DailyResponse(val status: String, val result: Result) {
    /**定义在DailyResponse内部,防止出现和其它接口的数据模型类有同名冲突的情况*/

    data class Result(val daily: Daily)

    //用List集合对JSON中的数组元素进行映射
    data class Daily(
        val astro: List<Astro>,
        val temperature: List<Temperature>,
        val skycon: List<Skycon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )
    data class Astro(val sunrise: Time, val sunset: Time)

    data class Time(val time: String)

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: Date)

    //用List集合对JSON中的数组元素进行映射
    data class LifeIndex(
        val coldRisk: List<LifeDescription>,
        val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>,
        val dressing: List<LifeDescription>
    )

    data class LifeDescription(val desc: String)
}