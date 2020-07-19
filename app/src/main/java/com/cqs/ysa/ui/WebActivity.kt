package com.cqs.ysa.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.cqs.ysa.R
import com.cqs.ysa.base.BaseActivity
import com.cqs.ysa.statusbar.StatusBarUtil
import kotlinx.android.synthetic.main.activity_web.*

/**
 * Created by bingo on 2020/7/18.
 */

/**
 * Created by bingo on 2020/6/22 0022.
 * webview 视图
 */

class WebActivity : BaseActivity(){
    var baseUrl:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary))//设置背景颜色
        initWebView()
        initView()
    }

    private fun initView() {
        baseUrl = intent.extras["url"] as String
       if (!baseUrl.isNullOrEmpty()){
           webView.loadUrl(baseUrl)
       }
//        val intent = getIntent()
//        if (intent != null) {
//            baseUrl = intent!!.getStringExtra(baseUrl)
//            if (!TextUtils.isEmpty(baseUrl)) {
//                webView!!.loadUrl(baseUrl)
//            }
//        }
    }

    private fun initWebView() {
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
                // return super.shouldOverrideUrlLoading(view, request);
            }
        }
        //声明WebSettings子类
        val webSettings = webView!!.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.javaScriptEnabled = true//支持插件
        webSettings.domStorageEnabled = true // 开启DOM缓存,默认状态下是不支持LocalStorage的
        webSettings.databaseEnabled = true // 开启数据库缓存
        //webSettings.setPluginsEnabled(true);//设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        webSettings.savePassword = false// 关闭密码保存提醒功能
        webSettings.loadsImagesAutomatically = true // 支持自动加载图片

        //webSettings.setUserAgentString(""); // 设置 UserAgent 属性
        webSettings.allowFileAccess = true // 允许加载本地 html 文件/false
        // 允许通过 file url 加载的 Javascript 读取其他的本地文件,Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        //webSettings.setAllowFileAccessFromFileURLs(false);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.allowFileAccessFromFileURLs = true
        }
        // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源，
        // Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
        // 如果此设置是允许，则 setAllowFileAccessFromFileURLs 不起做用
        webSettings.allowUniversalAccessFromFileURLs = false
        // 缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = false //隐藏原生的缩放控件
        //其他细节操作
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webview中缓存
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.defaultTextEncodingName = "utf-8"//设置编码格式

        //优先使用缓存
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据
        //不使用缓存
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //注入js交互
        //webView.addJavascriptInterface(new JsBridgeInterface(),"Android");
        // 本地 html alert来调试数据时，一直没有作用。
        //通过查API，知道 有个setWebChromeClient的方法，官方解释为Sets the chrome handler. This is an implementation of WebChromeClient for use in handling JavaScript dialogs, favicons, titles, and the progress.This will replace the current handler.
        // 大体意思是这个方法是来处理 javascript 方法中的对话框，收藏夹，标题和进度条之类的
        webView!!.webChromeClient = object : WebChromeClient() {
            override fun onJsPrompt(view: WebView, url: String, message: String, defaultValue: String, result: JsPromptResult): Boolean {
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }
        }
        /**
         * 防止遇到重定向
         */
        webView!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) {
                    webView!!.goBack()
                    return@OnKeyListener true
                }
            }
            false
        })
    }

   override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再destory()
            val parent = webView!!.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(webView)
            }

            webView!!.stopLoading()
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView!!.settings.javaScriptEnabled = false
            webView!!.clearHistory()
            webView!!.clearView()
            webView!!.removeAllViews()
            try {
                webView!!.destroy()
            } catch (e: Throwable) {
                e.stackTrace
            }
        }
    }
}
