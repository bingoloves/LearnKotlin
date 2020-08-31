package com.cqs.ysa

import android.app.Application
import android.content.Context
import android.support.annotation.NonNull
import android.support.multidex.MultiDex
import com.cqs.ysa.utils.PreviewImageLoader
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.ConsolePrinter
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
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
        //日志
        initXLog()
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

    /**
     * 初始化日志
     */
    private fun initXLog() {
        val config = LogConfiguration.Builder()
                .logLevel(if(BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
                .tag("bingo")                                          // 指定 TAG，默认为 "X-LOG"
                .t()                                                        // 允许打印线程信息，默认禁止
                .st(2)                                               // 允许打印深度为2的调用栈信息，默认禁止
                .b()                                                        // 允许打印日志边框，默认禁止
                .build()

        val androidPrinter = AndroidPrinter()                               // 通过 android.util.Log 打印日志的打印器
        val filePrinter = FilePrinter.Builder(cacheDir.absolutePath + "/xlog/")// 指定保存日志文件的路径
                .fileNameGenerator(DateFileNameGenerator())         // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
                .backupStrategy(NeverBackupStrategy())              // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
                .build()
        XLog.init(config, // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
                androidPrinter, // 添加任意多的打印器。如果没有添加任何打印器，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
                filePrinter)
    }
}