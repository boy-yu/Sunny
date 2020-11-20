package com.example.sunny.Ui.Weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunny.Logic.Model.Location
import com.example.sunny.Logic.Repository

/**19.定义ViewModel层*/
class WeatherViewModel : ViewModel() {

    //传入经纬度参数封装成Location对象赋值给locationLiveData
    private val locationLiveData = MutableLiveData<Location>()

    //和界面相关的数据,放到ViewModel中可以保证手机旋转的时候不会丢失
    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    //使用Transformations.switchMap()观察对象
    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        //在switchMap()方法的转换函数中调用仓库层中定义的refreshWeather()方法
        Repository.refreshWeather(location.lng, location.lat, placeName)//仓库层返回的LiveData对象就可以转换成一个可供Activity观察的LiveData对象
    }

    //刷新天气信息
    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

}