package com.cqs.ysa.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.model.CustomMapStyleOptions
import com.amap.api.maps.model.Marker
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import com.cqs.ysa.statusbar.StatusBarUtil
import com.cqs.ysa.utils.ResourceUtils
import kotlinx.android.synthetic.main.activity_map.*
import android.view.Surface.ROTATION_270
import android.view.Surface.ROTATION_180
import android.view.Surface.ROTATION_90
import android.view.Surface.ROTATION_0
import android.support.v4.view.ViewCompat.getRotation
import android.content.Context.WINDOW_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.view.WindowManager
import android.view.Display
import android.view.Surface
import io.vov.vitamio.utils.Log


/**
 * Created by Administrator on 2020/8/21 0021.
 */
class MapActivity:BaseActivity() {
    var aMap:AMap ?= null
    var mSensorManager:SensorManager ?= null
    var mAccelerometer:Sensor ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        StatusBarUtil.setTranslucentStatus(this)
        mapView.onCreate(savedInstanceState)
        initMap()
    }



    fun initMap(){
        if (aMap == null) {
            aMap = mapView.map
        }
        val uiSettings = aMap!!.uiSettings
        uiSettings.isTiltGesturesEnabled = false// 禁用倾斜手势。
        uiSettings.isRotateGesturesEnabled = false// 禁用旋转手势。
        uiSettings.logoPosition = AMapOptions.LOGO_POSITION_BOTTOM_RIGHT// 设置地图logo显示在右下方
        uiSettings.isZoomControlsEnabled = false// 设置缩放按钮是否显示
        //自定义地图样式 地图版本7.0以后的方法 （是收费功能）
//        aMap?.setCustomMapStyle(CustomMapStyleOptions()
//                .setEnable(true)
//                .setStyleId("02b8ab734304f462664b3df8dcfc66b9"))
        //加载个性化的地图 低版本Api
//        aMap.setCustomMapStylePath(Environment.getExternalStorageDirectory() + File.separator+"style_json/style.json");
        //离线地图样式
        setMapCustomStyleFile()
    }
    private fun setMapCustomStyleFile(){
        val style = "map/black/style.data"
        val style_extra = "map/black/style_extra.data"
        val filePath = filesDir.absolutePath
        //复制离线地图到本地文件中
        ResourceUtils.copyFileFromAssets(this,style,filePath+"/"+style)
        ResourceUtils.copyFileFromAssets(this,style_extra,filePath+"/"+style_extra)
//        mapView.postDelayed({
            aMap!!.setCustomMapStyle(CustomMapStyleOptions()
                    .setEnable(true)
                    .setStyleDataPath(filePath+"/"+style)
                    .setStyleExtraPath(filePath+"/"+style_extra))
//                        .setStyleTexturePath("/mnt/sdcard/amap/textures.zip")
//        },1000)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    /**
     * 获取当前屏幕旋转角度
     *
     * @param context
     * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    private fun getScreenRotationOnPhone(context: Context): Int {
        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        when (display.rotation) {
            Surface.ROTATION_0 -> return 0

            Surface.ROTATION_90 -> return 90

            Surface.ROTATION_180 -> return 180

            Surface.ROTATION_270 -> return -90
        }
        return 0
    }
    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}