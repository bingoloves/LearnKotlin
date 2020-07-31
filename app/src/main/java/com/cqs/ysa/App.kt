package com.cqs.ysa

import android.app.Application
import android.content.Context
import android.support.annotation.NonNull
import android.support.multidex.MultiDex
import com.cqs.ysa.utils.PreviewImageLoader
import com.previewlibrary.ZoomMediaLoader
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator
import com.zzhoujay.richtext.RichText

/**
 * Created by Administrator on 2020/7/20 0020.
 */
class App :Application(){
    override fun onCreate() {
        super.onCreate()
        //图片预览框架
        ZoomMediaLoader.getInstance().init(PreviewImageLoader())
        //富文本框架
        RichText.initCacheDir(applicationContext)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    /**
     * SmartRefresh 刷新库全局配置头部
     * static 代码段可以防止内存泄露
     */
    companion object {
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
                ClassicsHeader(context).setArrowResource(R.drawable.ic_arrow_down).setEnableLastTime(false)/*不显示更新时间样式*///.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                ClassicsFooter(context).setDrawableSize(20f)//指定为经典Footer
            }
        }
    }
}