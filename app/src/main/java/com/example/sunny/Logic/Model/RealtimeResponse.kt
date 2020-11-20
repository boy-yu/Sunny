package com.example.sunny.Logic.Model

import com.google.gson.annotations.SerializedName

/**14.显示天气信息
 * 获取实时天气数据模型
 */

/**简化后的JSON
 * "status": "ok",
 * "result":{
 *      "realtime":{
 *           "temperature":23.16,
 *           "skycon":"WIND",
 *           "air_quality":{
 *              "aqi":{chn":17.0}
 *              }
 *            }
 *          }
 *        }
 */
data class RealtimeResponse(val status: String, val result: Result) {
    /**定义在RealtimeResponse内部,防止出现和其它接口的数据模型类有同名冲突的情况*/

    data class Result(val realtime: Realtime)

    data class Realtime(
        val skycon: String, val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}