package com.example.sunny.Ui.Weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunny.Logic.Model.Weather
import com.example.sunny.Logic.Model.getSky
import com.example.sunny.R
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import kotlinx.android.synthetic.main.time.*
import java.text.SimpleDateFormat
import java.util.*

/**25.请求天气数据,并将数据展示到界面上*/
class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //展示界面
        setContentView(R.layout.activity_weather)
        //隐藏标题
        supportActionBar?.hide()

        //从Intent中提取经纬度和地区名并赋值到WeatherViewModel的相应变量中
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        //对weatherLiveData进行观察
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {//当获取到服务器时
                //调用showWeatherInfo()方法进行解析与展示
                showWeatherInfo(weather)
            } else {//否则弹出提示
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            /**------------------下拉刷新天气------------------**/
            swipeRefresh.isRefreshing = false
        })
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        refreshWeather()
        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
        /***------------------(选择城市)滑动菜单逻辑处理------------------***/
        navBtn.setOnClickListener {
            //打开滑动菜单
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //监听DrawerLayout的状态
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {
                //滑动菜单被隐藏的时候输入法也药隐藏
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        })
    }

    /***------------------------------------------------------------------------***/
    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        swipeRefresh.isRefreshing = true
    }

    /**------------------------------------------------------------------------**/

    //添加数据
    private fun showWeatherInfo(weather: Weather) {
        placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        val hourly = weather.hourly
        // 填充now.xml布局中数据
        //温度
        val currentTempText = "${realtime.temperature.toInt()}"
        currentTemp.text = currentTempText
        //天气情况
        currentSky.text = getSky(realtime.skycon).info
        //空气指数
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        //体感温度
        val currentApparentText = "${realtime.apparent_temperature.toInt()}℃"
        currentApparent.text = currentApparentText
        //大气压
        val currentPressureText = "${(realtime.pressure / 100).toInt()}hPa"
        currentPressure.text = currentPressureText
        //相对湿度
        val currentHumidityText = "${(realtime.humidity * 100).toInt()}%"
        currentHumidity.text = currentHumidityText
        //日出
        val sunUpZoneText = "${daily.astro[0].sunrise.time}"
        sunUp.text = sunUpZoneText
        //日落
        val sunDownText = "${daily.astro[0].sunset.time}"
        sunDown.text = sunDownText

        // 填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        val days = daily.skycon.size

        //只显示15天的天气信息
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} / ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }

        // 填充time.xml布局中的数据
        timeLayout.removeAllViews()
        val hourlys = hourly.skycon.size

        //只显示24小时的天气信息
        for (i in 0 until hourlys) {
            val skycon = hourly.skycon[i]
            val temperature = hourly.temperature[i]
            val view = LayoutInflater.from(this).inflate(R.layout.time_item, timeLayout, false)
            val shijian = view.findViewById(R.id.timeInfo) as TextView
            val tubiao = view.findViewById(R.id.tubiao) as ImageView
            val wenzi = view.findViewById(R.id.wenzi) as TextView
            val wendu = view.findViewById(R.id.wendu) as TextView
            val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            shijian.text = simpleDateFormat.format(skycon.datetime)
            val sky = getSky(skycon.value)
            tubiao.setImageResource(sky.icon)
            wenzi.text = sky.info
            val tempText = "${temperature.value.toInt()} ℃"
            wendu.text = tempText
            timeLayout.addView(view)
        }


        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }

}