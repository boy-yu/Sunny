package com.example.sunny.Logic.Model

import com.google.gson.annotations.SerializedName
import java.util.*

/**显示天气信息
 * 获取未来几天天气的数据模型
 */
data class DailyResponse(val status: String, val result: Result) {
    /**定义在DailyResponse内部,防止出现和其它接口的数据模型类有同名冲突的情况*/

    class Result(val daily: Daily)

    class Daily(
        val temperature: List<Temperature>,
        val skycon: List<Skycon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    class Temperature(val max: Float, val min: Float)

    class Skycon(val value: String, val date: Date)

    class LifeIndex(
        val coldRisk: List<LifeDescription>,
        val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>,
        val dressing: List<LifeDescription>
    )

    class LifeDescription(val desc: String)
}