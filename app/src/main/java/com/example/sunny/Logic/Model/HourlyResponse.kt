package com.example.sunny.Logic.Model

import java.util.*

data class HourlyResponse(val status: String, val result: Result) {
    /**定义在DailyResponse内部,防止出现和其它接口的数据模型类有同名冲突的情况*/

    data class Result(val hourly: Hourly)

    //用List集合对JSON中的数组元素进行映射
    data class Hourly(
        val skycon: List<Skycon>,
        val temperature: List<Temperature>,
    )
    data class Skycon(val value: String, val datetime: Date)

    data class Temperature(val value: Float)
}