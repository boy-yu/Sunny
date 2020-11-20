package com.example.sunny.Ui.Place


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunny.MainActivity
import com.example.sunny.R
import com.example.sunny.Ui.Weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*

/**11.对Fragment进行实现*/
class PlaceFragment : Fragment() {

    //获取PlaceViewModel实例
    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }


    private lateinit var adapter: PlaceAdapter

    //加载fragment_place布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        if (activity is MainActivity && viewModel.isPlaceSaved()) {//如果当前已有存储的数据
            //获取已存储的数据解析成Place对象
            val place = viewModel.getSavedPlace()
            //用它的经纬度坐标和城市名直接跳转并传递个WeatherActivity
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        //设置LinearLayoutManager和适配器
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        //使用PlaceViewModel的placeList集合作为数据源
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        //监听搜索框内容的变化情况
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {//当搜索框中的内容发生了变化
                //获取新的内容,传递给PlaceViewModel的searchPlaces()方法
                viewModel.searchPlaces(content)
            } else {//当搜索框中的内容为空时
                //将RecyclerView隐藏起来
                recyclerView.visibility = View.GONE
                //将背景图显示出来
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        //获取服务器响应
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->//当数据有变化时,回调到传入的Observer接口中实现
            //将数据添加到集合中
            val places = result.getOrNull()
            //对回调的数据进行判断
            if (places != null) {//如果不为空
                //通知PlaceAdapter刷新界面
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {//如果为空
                //弹出一个Toast提示,并将具体的异常原因打印出来
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

}