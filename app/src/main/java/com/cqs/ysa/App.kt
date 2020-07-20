package com.cqs.ysa

import android.app.Application
import com.cqs.ysa.utils.PreviewImageLoader
import com.previewlibrary.ZoomMediaLoader

/**
 * Created by Administrator on 2020/7/20 0020.
 */
class App :Application(){
    override fun onCreate() {
        super.onCreate()
        //图片预览框架
        ZoomMediaLoader.getInstance().init(PreviewImageLoader())
    }
}