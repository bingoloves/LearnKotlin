package com.cqs.ysa.fragment

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.util.Log
import android.view.View
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseFragment
import com.cqs.ysa.bean.Weather
import com.cqs.ysa.retrofit.BaseObserver
import com.cqs.ysa.retrofit.RetrofitUtil
import com.github.mikephil.charting.components.AxisBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import kotlinx.android.synthetic.main.fragment_weather.*
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


/**
 * Created by bingo on 2020/7/15 0015.
 */
class WeatherFragment : BaseFragment(){
    var list = ArrayList<Weather.ResultBean.FutureBean>()
    override fun contentView(): Int {
        return R.layout.fragment_weather
    }
    override fun initView(view: View?) {
        initLineChart()
        getWeather()
//        var animDrawable = loading.drawable as AnimationDrawable
//        animDrawable.start()
    }

    override fun lazyLoad() {
    }

    /**
     * 初始化折线图控件属性
     */
    private fun initLineChart() {
        //lineChart.setOnChartValueSelectedListener(this)
        lineChart.description.isEnabled = false
        lineChart.setBackgroundColor(Color.WHITE)
        //自定义适配器，适配于X轴
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = 55f
        xAxis.valueFormatter = IAxisValueFormatter { value, axis -> list[value.toInt()].date }
        xAxis.setDrawGridLines(false)
        //自定义适配器，适配于Y轴
        //val custom = MyAxisValueFormatter()

        val leftAxis = lineChart.axisLeft
        leftAxis.setLabelCount(8, false)
        //leftAxis.setValueFormatter(custom)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(false)
        lineChart.axisRight.isEnabled = false
    }

    /**
     * 设置折线图的数据
     */
    private fun setLineChartData() {
        if(!isAdded) return
        val minValues = ArrayList<Entry>()
        val maxValues = ArrayList<Entry>()
        for ((index,bean) in list.withIndex()){
            val arr = bean.temperature.replace("℃","",true).split("/")
            minValues.add(Entry(index.toFloat(), arr[0].toFloat()))
            maxValues.add(Entry(index.toFloat(), arr[1].toFloat()))
        }
        //每一个LineDataSet相当于一组折线。
        val minLineDataSet = LineDataSet(minValues, "最小温度")
        minLineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        minLineDataSet.color = resources.getColor(R.color.colorAccent)
        minLineDataSet.setDrawCircles(false)
        minLineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER

        val maxLineDataSet = LineDataSet(maxValues, "最大温度")
        maxLineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        maxLineDataSet.color = resources.getColor(R.color.colorPrimary)
        maxLineDataSet.setDrawCircles(false)
        maxLineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(minLineDataSet)
        dataSets.add(maxLineDataSet)
        val lineData = LineData(dataSets)
        lineChart.data = lineData
        lineChart.invalidate()
    }
    fun getWeather(){
        RetrofitUtil.get().apiService.getWeather("合肥")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : BaseObserver<Weather>(){
                    override fun onSuccess(data: Weather?) {
                        if ( data!!.error_code == 0){
                            list = data.result.future as ArrayList<Weather.ResultBean.FutureBean>
                            setLineChartData()
                        } else{
                            toast(data.reason)
                        }
                    }

                    override fun onFailure(error: String?) {
                        Log.e("TAG",error)
                    }
                })
    }
}