package com.example.sunny.Ui.Place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunny.Logic.Model.Place
import com.example.sunny.R
import com.example.sunny.Ui.Weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*


/** 10.显示搜索到的地区名/显示该地区的详细地址 */
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    /********************显示城市********************/
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //搜索到的地面
        val placeName: TextView = view.findViewById(R.id.placeName)
        //该地区的详细地址
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.place_item,
            parent, false
        )


        /********************26.显示天气********************/
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]
            /********************关闭滑动菜单的时候********************/
            //
            val activity = fragment.activity
            //获取当前地区的经纬度和地区名称
            if (activity is WeatherActivity) {//判断是否在WeatherActivity关闭了滑动菜单
                //给WeatherActivity赋值新的经纬度和地区名称
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                //并传入到Intent中
                activity.refreshWeather()
            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                //启动WeatherActivity
                fragment.startActivity(intent)
                activity?.finish()
            }
            //刷新天气信息
            fragment.viewModel.savePlace(place)
            /****************************************************************************************************/
        }
        return holder
        /********************显示天气********************/
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount() = placeList.size
    /********************显示城市********************/

}