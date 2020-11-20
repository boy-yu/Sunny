package com.example.sunny.Ui.Place



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunny.Logic.Model.Place
import com.example.sunny.Logic.Repository

/** 7.ViewModel()逻辑层与Ui层的桥梁 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    //界面上显示城市的数据进行缓存
    val placeList = ArrayList<Place>()

    //每当searchPlaces()函数被调用时,switchMap()方法所对应的转换函数就会执行
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        //调用searchPlaces()方法发起网络请求,同时将仓库层返回的LiveData对象转换成一个可供Activity观察的LiveData对象
        Repository.searchPlaces(query)
    }

    //将传入的搜索参数复制给searchLiveData对象
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }


    /**28.存储/读取接口封装*/
    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()
}